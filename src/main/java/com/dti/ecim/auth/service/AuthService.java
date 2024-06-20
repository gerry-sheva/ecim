package com.dti.ecim.auth.service;

import com.dti.ecim.auth.dto.AuthRequestDto;
import com.dti.ecim.auth.dto.AuthResponseDto;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpHeaders;

public interface AuthService {
    AuthResponseDto registerUser(AuthRequestDto requestDto) throws BadRequestException;
    AuthResponseDto authenticateUser(AuthRequestDto requestDto);
    HttpHeaders saveTokenToCookie(AuthResponseDto responseDto);
}
