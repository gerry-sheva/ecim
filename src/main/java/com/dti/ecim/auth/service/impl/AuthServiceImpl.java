package com.dti.ecim.auth.service.impl;

import com.dti.ecim.auth.dto.*;
import com.dti.ecim.auth.entity.UserAuth;
import com.dti.ecim.auth.enums.Role;
import com.dti.ecim.auth.repository.AuthRedisRepository;
import com.dti.ecim.auth.repository.UserAuthRepository;
import com.dti.ecim.auth.service.AuthService;
import com.dti.ecim.dto.ResponseDto;
import com.dti.ecim.exceptions.DataNotFoundException;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.apache.coyote.BadRequestException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Log
public class AuthServiceImpl implements AuthService {
    private final UserAuthRepository userAuthRepository;
    private final AuthRedisRepository authRedisRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtEncoder jwtEncoder;

    @Override
    public AuthResponseDto registerUser(RegisterRequestDto requestDto) throws BadRequestException{
        if (!requestDto.getPassword().equals(requestDto.getConfirmPassword())) {
            throw new BadRequestException("Passwords do not match");
        }
        String password = passwordEncoder.encode(requestDto.getPassword());
        UserAuth userAuth = new UserAuth();
        userAuth.setEmail(requestDto.getEmail());
        userAuth.setPassword(password);
        userAuthRepository.save(userAuth);
        AuthResponseDto res = authenticateUser(new LoginRequestDto(requestDto.getEmail(), requestDto.getPassword()));
        res.setMessage("Registered user");
        return res;
    }

    @Override
    public AuthResponseDto authenticateUser(LoginRequestDto requestDto) {
        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        requestDto.getEmail(),
                        requestDto.getPassword())
        );

        var ctx = SecurityContextHolder.getContext();
        ctx.setAuthentication(auth);
        AuthResponseDto res = new AuthResponseDto();
        String token;
        Optional<String> jwtKeyOptional = authRedisRepository.getJwtKey(requestDto.getEmail());
        token = jwtKeyOptional.orElseGet(() -> generateToken(auth));
        res.setToken(token);
        res.setMessage("Login successful");
        authRedisRepository.saveJwtKey(requestDto.getEmail(), token);
        return res;
    }

    @Override
    public HttpHeaders saveTokenToCookie(AuthResponseDto res) {
        Cookie cookie = new Cookie("sid", res.getToken());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Set-Cookie", cookie.getName() + "=" + cookie.getValue() + "; Path=/; HttpOnly");
        return headers;
    }

    @Override
    public void logoutUser(String jwtKey) {
        var context = SecurityContextHolder.getContext();
        Authentication auth = context.getAuthentication();
        log.info("Logging out " + auth.getName());
        log.info("Adding jwtKey to blocklist: " + jwtKey);
        authRedisRepository.saveBlacklistKey(jwtKey);
        log.info("Removing jwtKey from cache: " + jwtKey);
        authRedisRepository.deleteJwtKey(auth.getName());
        SecurityContextHolder.clearContext();
    }

    private String generateToken(Authentication auth) {
        Instant now = Instant.now();

        String scope = auth.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        JwtClaimsSet claimsSet = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(1, ChronoUnit.HOURS))
                .subject(auth.getName())
                .claim("scope", scope)
                .build();
        return jwtEncoder.encode(JwtEncoderParameters.from(claimsSet)).getTokenValue();
    }

    @Override
    public ResponseDto resetPassword(ResetPasswordRequestDto requestDto) throws BadRequestException {
        if (!requestDto.getPassword().equals(requestDto.getConfirmPassword())) {
            throw new BadRequestException("Password doesn't match");
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<UserAuth> userAuthOptional = userAuthRepository.findByEmail(auth.getName());
        if (userAuthOptional.isEmpty()) {
            throw new DataNotFoundException("User not found");
        }
        UserAuth userAuth = userAuthOptional.get();
        userAuth.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        userAuthRepository.save(userAuth);
        return new ResponseDto("Password reset successful");
    }

    @Override
    public UserIdResponseDto getCurrentUserId() {
        Optional<UserAuth> userAuthOptional = userAuthRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if (userAuthOptional.isEmpty()) {
            throw new DataNotFoundException("User not found");
        }
        return new UserIdResponseDto(userAuthOptional.get().getId(), userAuthOptional.get().getEmail(), userAuthOptional.get().getRole());
    }

    @Override
    public UserAuth getCurrentUser() {
        Optional<UserAuth> userAuthOptional = userAuthRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if (userAuthOptional.isEmpty()) {
            throw new DataNotFoundException("User not found");
        }
        return userAuthOptional.get();
    }

    @Override
    public HttpHeaders addRole(Role role) {
//        Add role to db
        UserAuth userAuth = getCurrentUser();
        userAuth.setRole(role);
        userAuthRepository.save(userAuth);

//        Update security context with new role
        var auth = SecurityContextHolder.getContext().getAuthentication();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role.name()));
        Authentication newAuth = new UsernamePasswordAuthenticationToken(
                auth.getName(),
                auth.getCredentials(),
                authorities
        );
        SecurityContextHolder.getContext().setAuthentication(newAuth);

//        Generate new JWT
        String token = generateToken(newAuth);
        authRedisRepository.saveJwtKey(auth.getName(), token);

        Cookie cookie = new Cookie("sid", token);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Set-Cookie", cookie.getName() + "=" + cookie.getValue() + "; Path=/; HttpOnly");
        return headers;
    }
}
