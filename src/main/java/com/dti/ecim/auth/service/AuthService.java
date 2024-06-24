package com.dti.ecim.auth.service;

import com.dti.ecim.auth.dto.AuthResponseDto;
import com.dti.ecim.auth.dto.LoginRequestDto;
import com.dti.ecim.auth.dto.RegisterRequestDto;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.GrantedAuthority;

public interface AuthService {
    AuthResponseDto registerUser(RegisterRequestDto requestDto) throws BadRequestException;
    AuthResponseDto authenticateUser(LoginRequestDto requestDto);
    HttpHeaders saveTokenToCookie(AuthResponseDto responseDto);
}
