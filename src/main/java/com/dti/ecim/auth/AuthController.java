package com.dti.ecim.auth;

import com.dti.ecim.auth.dto.AuthResponseDto;
import com.dti.ecim.auth.dto.LoginRequestDto;
import com.dti.ecim.auth.dto.RegisterRequestDto;
import com.dti.ecim.auth.dto.ResetPasswordRequestDto;
import com.dti.ecim.auth.service.AuthService;
import com.dti.ecim.dto.ResponseDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    @Transactional
    public ResponseEntity<AuthResponseDto> register(@RequestBody RegisterRequestDto registerRequestDto) throws BadRequestException {
        AuthResponseDto res =  authService.registerUser(registerRequestDto);
        HttpHeaders headers = authService.saveTokenToCookie(res);
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(res);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        AuthResponseDto res = authService.authenticateUser(loginRequestDto);
        HttpHeaders headers = authService.saveTokenToCookie(res);
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(res);
    }

    @PostMapping("/logout")
    public ResponseEntity<ResponseDto> logout(@CookieValue("sid") String jwtKey) {
        authService.logoutUser(jwtKey);
        ResponseDto res = new ResponseDto("Logged out successfully");
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @PostMapping("/reset")
    public ResponseEntity<ResponseDto> resetPassword(@RequestBody ResetPasswordRequestDto resetPasswordRequestDto) throws BadRequestException {
        ResponseDto res = authService.resetPassword(resetPasswordRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }
}
