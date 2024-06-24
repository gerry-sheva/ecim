package com.dti.ecim.config;

import com.dti.ecim.auth.repository.AuthRedisRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {
    private final AuthRedisRepository authRedisRepository;

    public JwtFilter(AuthRedisRepository authRedisRepository) {
        this.authRedisRepository = authRedisRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("sid")) {
                    String jwtKey = cookie.getValue();
                    boolean isBlacklisted = authRedisRepository.checkBlacklistKey(jwtKey);
                    if (isBlacklisted) {
                        response.setStatus(HttpStatus.UNAUTHORIZED.value());
                        response.getWriter().write("JWT token is no longer valid");
                        return;
                    }
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
