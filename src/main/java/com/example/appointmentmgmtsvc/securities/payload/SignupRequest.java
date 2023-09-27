/*
 * Copyright (c) Wi Yi 2023.
 */

package com.example.appointmentmgmtsvc.securities.payload;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class SignupRequest {
    private String username;
    private String email;
    private String password;
    private Set<String> roles;
}
