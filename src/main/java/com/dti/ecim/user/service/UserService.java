package com.dti.ecim.user.service;

import com.dti.ecim.user.dto.UserIdResponseDto;
import com.dti.ecim.user.entity.User;

public interface UserService {
    User registerUser();
    UserIdResponseDto getCurrentUserId();
}
