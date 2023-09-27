/*
 * Copyright (c) Wi Yi 2023.
 */

package com.example.appointmentmgmtsvc.repositories;

import com.example.appointmentmgmtsvc.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {

}
