/*
 * Copyright (c) Wi Yi 2023.
 */

package com.example.appointmentmgmtsvc.securities.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class JWTResponse {
    private String token;
    private Long id;
    private String username;
    private String email;
    private List<String> roles;
}
