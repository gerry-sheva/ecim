package com.dti.ecim.auth.service.impl;

import com.dti.ecim.auth.dto.AuthResponseDto;
import com.dti.ecim.auth.dto.LoginRequestDto;
import com.dti.ecim.auth.dto.RegisterRequestDto;
import com.dti.ecim.auth.entity.UserAuth;
import com.dti.ecim.auth.repository.AuthRedisRepository;
import com.dti.ecim.auth.repository.UserAuthRepository;
import com.dti.ecim.auth.service.AuthService;
import com.dti.ecim.user.entity.User;
import com.dti.ecim.user.repository.UserRepository;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.apache.coyote.BadRequestException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Log
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
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
        User user = userRepository.save(new User());
        UserAuth userAuth = new UserAuth();
        userAuth.setUser(user);
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
        log.info("Adding jwtKey to blocklist: " + jwtKey);
        authRedisRepository.saveBlacklistKey(jwtKey);
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
}
