/*
 * Copyright (c) Wi Yi 2023.
 */

package com.example.appointmentmgmtsvc.repositories;

import com.example.appointmentmgmtsvc.entities.ApiUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<ApiUser, Long> {
    Optional<ApiUser> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}
