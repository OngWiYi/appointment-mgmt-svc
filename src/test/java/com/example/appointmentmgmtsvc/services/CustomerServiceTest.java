/*
 * Copyright (c) Wi Yi 2023.
 */

package com.example.appointmentmgmtsvc.services;

import com.example.appointmentmgmtsvc.entities.Customer;
import com.example.appointmentmgmtsvc.model.CustomerDTO;
import com.example.appointmentmgmtsvc.repositories.CustomerRepository;
import com.example.appointmentmgmtsvc.services.service_impl.CustomerServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {
    private static final String orderAsc = "asc";
    private static final String orderDesc = "desc";
    private static final String sort = "column1";
    private static final String custId = "id1";

    @Mock
    private CustomerRepository customerRepository;
    private CustomerService customerService;
    private Customer customer;
    private CustomerDTO customerDTO;

    @BeforeEach
    void setUp() {
        this.customerDTO = new CustomerDTO("fName", "phoneNum");
        this.customer = new Customer();
        customer.setId(custId);
        customer.setFirstName("fName");
        customer.setPhoneNumber("phoneNum");
        this.customerService = new CustomerServiceImpl(this.customerRepository, new ModelMapper());
    }

    @Test
    void getCustomersShouldCallFindAllWithCorrectSortingOrder() {
        customerService.getCustomers(sort, orderAsc);
        verify(customerRepository).findAll(Sort.by(sort).ascending());
        customerService.getCustomers(sort, orderDesc);
        verify(customerRepository).findAll(Sort.by(sort).descending());
    }

    @Test
    void getCustomersShouldThrowIllegalArgumentExceptionWhenSortIsNull() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> customerService.getCustomers(null, orderAsc));
    }

    @Test
    void getCustomersShouldThrowIllegalArgumentExceptionWhenSortIsEmpty() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> customerService.getCustomers("", orderAsc));
    }

    @Test
    void getCustomersShouldThrowNullPointerExceptionWhenOrderIsNull() {
        Assertions.assertThrows(NullPointerException.class, () -> customerService.getCustomers(sort, null));
    }

    @Test
    void getCustomerShouldCallFindById() {
        customerService.getCustomer(custId);
        verify(customerRepository).findById(custId);
    }

    @Test
    void createCustomerShouldCallSave() {
        when(customerRepository.save(any())).thenReturn(customer);

        customerService.createCustomer(customerDTO);
        verify(customerRepository).save(any());
    }

    @Test
    void updateCustomerShouldCallSave() {
        when(customerRepository.save(any())).thenReturn(customer);

        customerService.updateCustomer(custId, customerDTO);
        verify(customerRepository).save(any());
    }

    @Test
    void customerExistShouldCallExistsById() {
        customerService.customerExist(custId);
        verify(customerRepository).existsById(custId);
    }
}