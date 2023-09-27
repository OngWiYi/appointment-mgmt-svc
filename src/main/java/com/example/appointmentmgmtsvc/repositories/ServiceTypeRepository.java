/*
 * Copyright (c) Wi Yi 2023.
 */

package com.example.appointmentmgmtsvc.repositories;

import com.example.appointmentmgmtsvc.entities.ServiceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceTypeRepository extends JpaRepository<ServiceType, String> {
}
