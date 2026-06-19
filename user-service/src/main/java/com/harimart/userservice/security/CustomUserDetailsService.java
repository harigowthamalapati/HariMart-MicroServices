package com.harimart.userservice.security;

import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import com.harimart.userservice.entity.User;
import com.harimart.userservice.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService
        implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "User not found"));

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .roles(
                        user.getRole()
                                .name()
                                .replace("ROLE_", "")
                )
                .build();
    }
}