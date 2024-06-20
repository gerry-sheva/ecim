package com.dti.ecim.auth;

import com.dti.ecim.auth.dto.AuthRequestDto;
import com.dti.ecim.auth.dto.AuthResponseDto;
import com.dti.ecim.auth.service.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    @Transactional
    public ResponseEntity<AuthResponseDto> register(@RequestBody AuthRequestDto registerRequestDto) throws BadRequestException {
        AuthResponseDto res =  authService.registerUser(registerRequestDto);

        Cookie cookie = new Cookie("sid", res.getToken());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Set-Cookie", cookie.getName() + "=" + cookie.getValue() + "; Path=/; HttpOnly");
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(res);
    }

}
