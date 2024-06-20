package com.dti.ecim.auth.service.impl;

import com.dti.ecim.auth.dto.AuthRequestDto;
import com.dti.ecim.auth.dto.AuthResponseDto;
import com.dti.ecim.auth.entity.UserAuth;
import com.dti.ecim.auth.repository.UserAuthRepository;
import com.dti.ecim.auth.service.AuthService;
import com.dti.ecim.user.entity.User;
import com.dti.ecim.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final UserAuthRepository userAuthRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtEncoder jwtEncoder;

    @Override
    public AuthResponseDto registerUser(AuthRequestDto requestDto) throws BadRequestException{
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
        AuthResponseDto res = authenticateUser(requestDto);
        res.setMessage("Registered user");
        return res;
    }

    @Override
    public AuthResponseDto authenticateUser(AuthRequestDto requestDto) {
        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        requestDto.getEmail(),
                        requestDto.getPassword())
        );
        var ctx = SecurityContextHolder.getContext();
        ctx.setAuthentication(auth);
        AuthResponseDto res = new AuthResponseDto();
        res.setToken(generateToken(auth));
        res.setMessage("Login successful");
        return res;
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
                .expiresAt(now.plus(6, ChronoUnit.HOURS))
                .subject(auth.getName())
                .claim("scope", scope)
                .build();
        return jwtEncoder.encode(JwtEncoderParameters.from(claimsSet)).getTokenValue();
    }
}
