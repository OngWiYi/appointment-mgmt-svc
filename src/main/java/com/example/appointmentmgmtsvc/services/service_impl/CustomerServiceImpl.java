/*
 * Copyright (c) Wi Yi 2023.
 */

package com.example.appointmentmgmtsvc.services.service_impl;

import com.example.appointmentmgmtsvc.entities.Customer;
import com.example.appointmentmgmtsvc.model.CustomerDTO;
import com.example.appointmentmgmtsvc.repositories.CustomerRepository;
import com.example.appointmentmgmtsvc.services.CustomerService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private final CustomerRepository customerRepository;

    @Autowired
    private final ModelMapper modelMapper;

    @Override
    public List<CustomerDTO> getCustomers(String sortColumn, String order) {
        Sort sort = order.equalsIgnoreCase(Direction.ASC.name()) ? Sort.by(sortColumn).ascending() :
                Sort.by(sortColumn).descending();
        return customerRepository.findAll(sort).stream().map(e -> modelMapper.map(e, CustomerDTO.class)).toList();
    }

    @Override
    public Optional<CustomerDTO> getCustomer(String id) {
        Optional<Customer> customerOptional = customerRepository.findById(id);
        CustomerDTO customerDTO = customerOptional.map(e -> modelMapper.map(e, CustomerDTO.class)).orElse(null);
        return Optional.ofNullable(customerDTO);
    }

    @Override
    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        Customer customer = modelMapper.map(customerDTO, Customer.class);
        customer.setCreatedDate(new Date());
        return modelMapper.map(customerRepository.save(customer), CustomerDTO.class);
    }

    @Override
    public CustomerDTO updateCustomer(String id, CustomerDTO customerDTO) {
        Customer customer = modelMapper.map(customerDTO, Customer.class);
        customer.setId(id);
        customer.setUpdatedDate(new Date());
        return modelMapper.map(customerRepository.save(customer), CustomerDTO.class);
    }

    @Override
    public boolean customerExist(String id) {
        return customerRepository.existsById(id);
    }

}
