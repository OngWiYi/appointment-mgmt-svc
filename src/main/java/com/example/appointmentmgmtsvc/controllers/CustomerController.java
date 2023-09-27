/*
 * Copyright (c) Wi Yi 2023.
 */

package com.example.appointmentmgmtsvc.controllers;

import com.example.appointmentmgmtsvc.api.CustomersApi;
import com.example.appointmentmgmtsvc.exceptions.ResourceNotFoundException;
import com.example.appointmentmgmtsvc.model.CustomerDTO;
import com.example.appointmentmgmtsvc.model.CustomerListResponse;
import com.example.appointmentmgmtsvc.model.CustomerResponse;
import com.example.appointmentmgmtsvc.services.CustomerService;
import com.example.appointmentmgmtsvc.utils.SortingUtil;
import com.example.appointmentmgmtsvc.utils.SortingUtil.SortParams;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Customer Controller
 */
@Controller
@RequestMapping(value = "api/v0/")
@AllArgsConstructor
public class CustomerController implements CustomersApi {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);

    private static final String CUSTOMER_NOT_FOUND_ERROR_MSG = "Customer not found";
    private static final List<String> ALLOWED_SEARCH_COLUMNS = List.of("firstName",
                                                                       "lastName",
                                                                       "createdDate",
                                                                       "updatedDate");

    @Autowired
    private final CustomerService customerService;

    @Override
    public ResponseEntity<CustomerResponse> addCustomer(CustomerDTO customer) {
        CustomerDTO newCustomer = customerService.createCustomer(customer);
        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.setData(newCustomer);
        return new ResponseEntity<>(customerResponse, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<CustomerResponse> getCustomer(UUID customerId) {
        Optional<CustomerDTO> customer = customerService.getCustomer(customerId.toString());
        if (customer.isEmpty()) {
            LOGGER.error(CUSTOMER_NOT_FOUND_ERROR_MSG);
            throw new ResourceNotFoundException(CUSTOMER_NOT_FOUND_ERROR_MSG);
        }
        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.setData(customer.get());
        return new ResponseEntity<>(customerResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CustomerListResponse> getCustomers(String sort, String order) {
        SortParams sortParams = SortingUtil.validateSortParams(sort, order, ALLOWED_SEARCH_COLUMNS);
        List<CustomerDTO> customers = customerService.getCustomers(sortParams.getSort(), sortParams.getOrder());
        CustomerListResponse customerListResponse = new CustomerListResponse();
        customerListResponse.setData(customers);
        return new ResponseEntity<>(customerListResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CustomerResponse> updateCustomer(UUID customerId, CustomerDTO customer) {
        if (!customerService.customerExist(customerId.toString())) {
            LOGGER.error(CUSTOMER_NOT_FOUND_ERROR_MSG);
            throw new ResourceNotFoundException(CUSTOMER_NOT_FOUND_ERROR_MSG);
        }
        CustomerDTO updatedCustomer = customerService.updateCustomer(customerId.toString(), customer);
        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.setData(updatedCustomer);
        return new ResponseEntity<>(customerResponse, HttpStatus.OK);
    }
}
