/*
 * Copyright (c) Wi Yi 2023.
 */

package com.example.appointmentmgmtsvc.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTUtil {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private int jwtExpireHr;

    public String generateToken(String username) throws IllegalArgumentException, JWTCreationException {
        return JWT.create()
                  .withSubject("User")
                  .withClaim("username", username)
                  .withIssuedAt(new Date())
                  .withExpiresAt(new Date(new Date().getTime() + (jwtExpireHr * 3600000L)))
                  .withIssuer("appointment-mgmt-svc")
                  .sign(Algorithm.HMAC256(secret));
    }

    public String validateTokenAndRetrieveSubject(String token) throws JWTVerificationException {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret))
                                  .withSubject("User")
                                  .withIssuer("appointment-mgmt-svc")
                                  .build();
        DecodedJWT jwt = verifier.verify(token);
        return jwt.getClaim("username").asString();
    }
}
