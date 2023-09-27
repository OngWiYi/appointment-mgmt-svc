/*
 * Copyright (c) Wi Yi 2023.
 */

package com.example.appointmentmgmtsvc.services.service_impl;

import com.example.appointmentmgmtsvc.entities.ServiceProvided;
import com.example.appointmentmgmtsvc.model.ServiceDTO;
import com.example.appointmentmgmtsvc.repositories.ServiceRepository;
import com.example.appointmentmgmtsvc.services.ServiceService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ServiceServiceImpl implements ServiceService {
    @Autowired
    private final ServiceRepository serviceRepository;

    @Autowired
    private final ModelMapper modelMapper;

    @Override
    public ServiceDTO createService(ServiceDTO serviceDTO) {
        ServiceProvided serviceProvided = modelMapper.map(serviceDTO, ServiceProvided.class);
        serviceProvided.setCreatedDate(new Date());
        return modelMapper.map(serviceRepository.save(serviceProvided), ServiceDTO.class);
    }

    @Override
    public boolean serviceExist(String id) {
        return !serviceRepository.existsById(id);
    }

    @Override
    public void deleteService(String id) {
        serviceRepository.deleteById(id);
    }

    @Override
    public List<ServiceDTO> getServices(String sortColumn, String order) {
        Sort sort = order.equalsIgnoreCase(Direction.ASC.name()) ? Sort.by(sortColumn).ascending() :
                Sort.by(sortColumn).descending();
        return serviceRepository.findAll(sort).stream().map(e -> modelMapper.map(e, ServiceDTO.class)).toList();
    }

    @Override
    public Optional<ServiceDTO> getService(String id) {
        Optional<ServiceProvided> serviceProvidedOptional = serviceRepository.findById(id);
        ServiceDTO serviceDTO = serviceProvidedOptional.map(e -> modelMapper.map(e, ServiceDTO.class)).orElse(null);
        return Optional.ofNullable(serviceDTO);
    }

    @Override
    public ServiceDTO updateService(String id, ServiceDTO serviceDTO) {
        ServiceProvided serviceProvided = modelMapper.map(serviceDTO, ServiceProvided.class);
        serviceProvided.setId(id);
        serviceProvided.setUpdatedDate(new Date());
        return modelMapper.map(serviceRepository.save(serviceProvided), ServiceDTO.class);
    }
}
