package com.dti.ecim.auth.service;

import com.dti.ecim.auth.dto.AuthResponseDto;
import com.dti.ecim.auth.dto.LoginRequestDto;
import com.dti.ecim.auth.dto.RegisterRequestDto;
import com.dti.ecim.auth.dto.ResetPasswordRequestDto;
import com.dti.ecim.dto.ResponseDto;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpHeaders;

public interface AuthService {
    AuthResponseDto registerUser(RegisterRequestDto requestDto) throws BadRequestException;
    AuthResponseDto authenticateUser(LoginRequestDto requestDto);
    HttpHeaders saveTokenToCookie(AuthResponseDto responseDto);
    void logoutUser(String jwtKey);
    ResponseDto resetPassword(ResetPasswordRequestDto requestDto) throws BadRequestException;
}
