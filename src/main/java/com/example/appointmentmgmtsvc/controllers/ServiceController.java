/*
 * Copyright (c) Wi Yi 2023.
 */

package com.example.appointmentmgmtsvc.controllers;

import com.example.appointmentmgmtsvc.api.ServicesApi;
import com.example.appointmentmgmtsvc.exceptions.ResourceNotFoundException;
import com.example.appointmentmgmtsvc.model.ServiceDTO;
import com.example.appointmentmgmtsvc.model.ServiceListResponse;
import com.example.appointmentmgmtsvc.model.ServiceResponse;
import com.example.appointmentmgmtsvc.services.ServiceService;
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

@Controller
@RequestMapping(value = "api/v0/")
@AllArgsConstructor
public class ServiceController implements ServicesApi {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceController.class);
    private static final String SERVICE_NOT_FOUND_ERROR_MSG = "Service not found";
    private static final List<String> ALLOWED_SEARCH_COLUMNS = List.of("name", "type", "price", "createdDate");

    @Autowired
    private final ServiceService serviceService;

    @Override
    public ResponseEntity<ServiceResponse> addService(ServiceDTO serviceDTO) {
        ServiceDTO newService = serviceService.createService(serviceDTO);
        ServiceResponse serviceResponse = new ServiceResponse();
        serviceResponse.setData(newService);
        return new ResponseEntity<>(serviceResponse, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> deleteService(UUID serviceId) {
        if (serviceService.serviceExist(serviceId.toString())) {
            LOGGER.error(SERVICE_NOT_FOUND_ERROR_MSG);
            throw new ResourceNotFoundException(SERVICE_NOT_FOUND_ERROR_MSG);
        }

        serviceService.deleteService(serviceId.toString());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<ServiceResponse> getService(UUID serviceId) {
        Optional<ServiceDTO> serviceDTO = serviceService.getService(serviceId.toString());
        if (serviceDTO.isEmpty()) {
            LOGGER.error(SERVICE_NOT_FOUND_ERROR_MSG);
            throw new ResourceNotFoundException(SERVICE_NOT_FOUND_ERROR_MSG);
        }
        ServiceResponse serviceResponse = new ServiceResponse();
        serviceResponse.setData(serviceDTO.get());
        return new ResponseEntity<>(serviceResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ServiceListResponse> getServices(String sort, String order) {
        SortParams sortParams = SortingUtil.validateSortParams(sort, order, ALLOWED_SEARCH_COLUMNS);
        List<ServiceDTO> serviceDTOList = serviceService.getServices(sortParams.getSort(), sortParams.getOrder());
        ServiceListResponse serviceListResponse = new ServiceListResponse();
        serviceListResponse.setData(serviceDTOList);
        return new ResponseEntity<>(serviceListResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ServiceResponse> updateService(UUID serviceId, ServiceDTO serviceDTO) {
        if (serviceService.serviceExist(serviceId.toString())) {
            LOGGER.error(SERVICE_NOT_FOUND_ERROR_MSG);
            throw new ResourceNotFoundException(SERVICE_NOT_FOUND_ERROR_MSG);
        }

        ServiceDTO updatedServiceDTO = serviceService.updateService(serviceId.toString(), serviceDTO);
        ServiceResponse serviceResponse = new ServiceResponse();
        serviceResponse.setData(updatedServiceDTO);
        return new ResponseEntity<>(serviceResponse, HttpStatus.OK);

    }
}
