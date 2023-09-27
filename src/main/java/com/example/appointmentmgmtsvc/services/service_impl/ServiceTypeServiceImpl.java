/*
 * Copyright (c) Wi Yi 2023.
 */

package com.example.appointmentmgmtsvc.services.service_impl;

import com.example.appointmentmgmtsvc.entities.ServiceType;
import com.example.appointmentmgmtsvc.model.ServiceTypeDTO;
import com.example.appointmentmgmtsvc.repositories.ServiceTypeRepository;
import com.example.appointmentmgmtsvc.services.ServiceTypeService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Service
public class ServiceTypeServiceImpl implements ServiceTypeService {
    @Autowired
    private final ServiceTypeRepository serviceTypeRepository;

    @Autowired
    private final ModelMapper modelMapper;

    @Override
    public ServiceTypeDTO createServiceType(ServiceTypeDTO serviceTypeDTO) {
        ServiceType serviceType = modelMapper.map(serviceTypeDTO, ServiceType.class);
        serviceType.setCreatedDate(new Date());
        return modelMapper.map(serviceTypeRepository.save(serviceType), ServiceTypeDTO.class);
    }

    @Override
    public boolean serviceTypeExist(String id) {
        return !serviceTypeRepository.existsById(id);
    }

    @Override
    public void deleteServiceType(String id) {
        serviceTypeRepository.deleteById(id);
    }

    @Override
    public List<ServiceTypeDTO> getCustomers() {
        return serviceTypeRepository.findAll().stream()
                .map(e -> modelMapper.map(e, ServiceTypeDTO.class))
                .toList();
    }

    @Override
    public ServiceTypeDTO updateServiceType(String id, ServiceTypeDTO serviceTypeDTO) {
        ServiceType serviceType = modelMapper.map(serviceTypeDTO, ServiceType.class);
        serviceType.setId(id);
        serviceType.setUpdatedDate(new Date());
        return modelMapper.map(serviceTypeRepository.save(serviceType), ServiceTypeDTO.class);
    }
}
