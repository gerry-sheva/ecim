package com.dti.ecim.auth.service;

import com.dti.ecim.auth.dto.*;
import com.dti.ecim.auth.entity.UserAuth;
import com.dti.ecim.auth.enums.Role;
import com.dti.ecim.dto.ResponseDto;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpHeaders;

public interface AuthService {
    AuthResponseDto registerUser(RegisterRequestDto requestDto) throws BadRequestException;
    AuthResponseDto authenticateUser(LoginRequestDto requestDto);
    HttpHeaders saveTokenToCookie(AuthResponseDto responseDto);
    void logoutUser(String jwtKey);
    ResponseDto resetPassword(ResetPasswordRequestDto requestDto) throws BadRequestException;
    UserIdResponseDto getCurrentUserId();
    UserAuth getCurrentUser();
    HttpHeaders addRole(Role role);
}
