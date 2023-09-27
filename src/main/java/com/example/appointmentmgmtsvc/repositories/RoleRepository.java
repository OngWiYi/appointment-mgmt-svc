/*
 * Copyright (c) Wi Yi 2023.
 */

package com.example.appointmentmgmtsvc.repositories;

import com.example.appointmentmgmtsvc.entities.Role;
import com.example.appointmentmgmtsvc.entities.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleEnum name);
}
