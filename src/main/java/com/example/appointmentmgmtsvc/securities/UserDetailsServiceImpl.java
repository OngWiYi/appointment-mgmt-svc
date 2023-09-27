/*
 * Copyright (c) Wi Yi 2023.
 */

package com.example.appointmentmgmtsvc.securities;

import com.example.appointmentmgmtsvc.entities.ApiUser;
import com.example.appointmentmgmtsvc.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        ApiUser apiUser = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(
                "User [" + username + "] Not Found"));
        return UserDetailsImpl.build(apiUser);

    }
}
