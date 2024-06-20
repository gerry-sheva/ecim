package com.dti.ecim.user.service.impl;

import com.dti.ecim.user.entity.User;
import com.dti.ecim.user.repository.UserRepository;
import com.dti.ecim.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public User registerUser() {
        return userRepository.save(new User());
    }
}
