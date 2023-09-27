/*
 * Copyright (c) Wi Yi 2023.
 */

package com.example.appointmentmgmtsvc.securities.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    private String username;
    private String password;

}
