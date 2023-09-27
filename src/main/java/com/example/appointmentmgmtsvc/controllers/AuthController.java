/*
 * Copyright (c) Wi Yi 2023.
 */

package com.example.appointmentmgmtsvc.controllers;

import com.example.appointmentmgmtsvc.entities.ApiUser;
import com.example.appointmentmgmtsvc.entities.Role;
import com.example.appointmentmgmtsvc.exceptions.BaseExceptionResponse;
import com.example.appointmentmgmtsvc.repositories.RoleRepository;
import com.example.appointmentmgmtsvc.repositories.UserRepository;
import com.example.appointmentmgmtsvc.securities.UserDetailsImpl;
import com.example.appointmentmgmtsvc.securities.payload.JWTResponse;
import com.example.appointmentmgmtsvc.securities.payload.LoginRequest;
import com.example.appointmentmgmtsvc.securities.payload.SignupRequest;
import com.example.appointmentmgmtsvc.utils.JWTUtil;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.example.appointmentmgmtsvc.entities.RoleEnum.ADMIN_ROLE;
import static com.example.appointmentmgmtsvc.entities.RoleEnum.USER_ROLE;

/**
 * Auth Controller
 */
@RestController
@RequestMapping("/api/v0/auth")
@AllArgsConstructor
public class AuthController {
    private static final String ROLE_NOT_FOUND_ERR_MSG = "Role not found";
    @Autowired
    private final AuthenticationManager authenticationManager;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final RoleRepository roleRepository;

    @Autowired
    private final PasswordEncoder encoder;

    @Autowired
    private final JWTUtil jwtUtil;

    @PostMapping("/signin")
    public ResponseEntity<Object> authenticateUser(@Valid
                                                   @RequestBody
                                                   LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(),
                loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtil.generateToken(authentication.getName());
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                                        .map(GrantedAuthority::getAuthority)
                                        .toList();
        return ResponseEntity.ok(new JWTResponse(jwt,
                                                 userDetails.getId(),
                                                 userDetails.getUsername(),
                                                 userDetails.getEmail(),
                                                 roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<Object> registerUser(@Valid
                                               @RequestBody
                                               SignupRequest signupRequest) {
        if (Boolean.TRUE.equals(userRepository.existsByUsername(signupRequest.getUsername()))) {
            return ResponseEntity
                    .badRequest()
                    .body(new BaseExceptionResponse(HttpStatus.BAD_REQUEST, "User already exists"));
        }
        if (Boolean.TRUE.equals(userRepository.existsByEmail(signupRequest.getEmail()))) {
            return ResponseEntity
                    .badRequest()
                    .body(new BaseExceptionResponse(HttpStatus.BAD_REQUEST, "Email already exists"));
        }
        ApiUser user = new ApiUser(signupRequest.getUsername(),
                                   signupRequest.getEmail(),
                                   encoder.encode(signupRequest.getPassword()));
        Set<String> strRoles = signupRequest.getRoles();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(USER_ROLE)
                                          .orElseThrow(() -> new RuntimeException(ROLE_NOT_FOUND_ERR_MSG));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                if (ADMIN_ROLE.toString().equals(role)) {
                    Role adminRole = roleRepository.findByName(ADMIN_ROLE)
                                                   .orElseThrow(() -> new RuntimeException(ROLE_NOT_FOUND_ERR_MSG));
                    roles.add(adminRole);
                } else {
                    Role userRole = roleRepository.findByName(USER_ROLE)
                                                  .orElseThrow(() -> new RuntimeException(ROLE_NOT_FOUND_ERR_MSG));
                    roles.add(userRole);
                }
            });
        }
        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(user);

    }
}
