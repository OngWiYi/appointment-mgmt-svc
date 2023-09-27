/*
 * Copyright (c) Wi Yi 2023.
 */

package com.example.appointmentmgmtsvc.services;

import com.example.appointmentmgmtsvc.model.CustomerDTO;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    List<CustomerDTO> getCustomers(String sortColumn, String order);

    Optional<CustomerDTO> getCustomer(String id);

    CustomerDTO createCustomer(CustomerDTO customerDTO);

    CustomerDTO updateCustomer(String id, CustomerDTO customerDTO);

    boolean customerExist(String id);
}
