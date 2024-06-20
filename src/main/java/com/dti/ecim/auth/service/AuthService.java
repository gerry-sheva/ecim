package com.dti.ecim.auth.service;

import com.dti.ecim.auth.dto.AuthRequestDto;
import com.dti.ecim.auth.dto.AuthResponseDto;
import com.dti.ecim.user.entity.User;
import org.apache.coyote.BadRequestException;
import org.springframework.security.core.Authentication;

public interface AuthService {
    AuthResponseDto registerUser(AuthRequestDto requestDto) throws BadRequestException;
    AuthResponseDto authenticateUser(AuthRequestDto requestDto);
}
