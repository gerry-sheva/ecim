package com.dti.ecim.user.service.impl;

import com.dti.ecim.auth.entity.UserAuth;
import com.dti.ecim.auth.repository.UserAuthRepository;
import com.dti.ecim.exceptions.DataNotFoundException;
import com.dti.ecim.user.dto.UserIdResponseDto;
import com.dti.ecim.user.entity.Attendee;
import com.dti.ecim.user.entity.User;
import com.dti.ecim.user.repository.AttendeeRepository;
import com.dti.ecim.user.repository.OrganizerRepository;
import com.dti.ecim.user.repository.ReferralRepository;
import com.dti.ecim.user.repository.UserRepository;
import com.dti.ecim.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserAuthRepository authRepository;
    private final AttendeeRepository attendeeRepository;
    private final OrganizerRepository organizerRepository;
    private final ReferralRepository referralRepository;

    @Override
    public User registerUser() {
        return userRepository.save(new User());
    }

    @Override
    public UserIdResponseDto getCurrentUserId() {
        Optional<UserAuth> userAuthOptional = authRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if (userAuthOptional.isEmpty()) {
            throw new DataNotFoundException("User not found");
        }
        return new UserIdResponseDto(userAuthOptional.get().getUserId());
    }
}
