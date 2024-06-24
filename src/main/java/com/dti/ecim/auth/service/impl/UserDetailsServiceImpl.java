package com.dti.ecim.auth.service.impl;

import com.dti.ecim.auth.entity.UserAuth;
import com.dti.ecim.auth.repository.UserAuthRepository;
import com.dti.ecim.auth.repository.UserRoleRepository;
import com.dti.ecim.auth.service.UserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserAuthRepository userAuthRepository;
    private final UserRoleService userRoleService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserAuth> userAuthOptional = userAuthRepository.findByEmail(username);
        if (userAuthOptional.isEmpty()) {
            throw new UsernameNotFoundException(username);
        }
        UserAuth userAuth = userAuthOptional.get();
        List<GrantedAuthority> roles = userRoleService.retrieveRoles(userAuth.getUserId());
        userAuth.setAuthorities(roles);
        return userAuth;
    }
}
