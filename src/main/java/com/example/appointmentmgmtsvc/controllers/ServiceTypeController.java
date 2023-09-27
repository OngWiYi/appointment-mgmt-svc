/*
 * Copyright (c) Wi Yi 2023.
 */

package com.example.appointmentmgmtsvc.controllers;

import com.example.appointmentmgmtsvc.api.ServiceTypesApi;
import com.example.appointmentmgmtsvc.exceptions.ResourceNotFoundException;
import com.example.appointmentmgmtsvc.model.ServiceTypeDTO;
import com.example.appointmentmgmtsvc.model.ServiceTypeListResponse;
import com.example.appointmentmgmtsvc.model.ServiceTypeResponse;
import com.example.appointmentmgmtsvc.services.ServiceTypeService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping(value = "api/v0/")
@AllArgsConstructor
public class ServiceTypeController implements ServiceTypesApi {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceTypeController.class);
    private static final String SERVICE_TYPE_NOT_FOUND_ERROR_MSG = "Service Type not found";

    @Autowired
    private final ServiceTypeService serviceTypeService;

    @Override
    public ResponseEntity<ServiceTypeResponse> addServiceType(ServiceTypeDTO serviceTypeDTO) {
        ServiceTypeDTO newServiceType = serviceTypeService.createServiceType(serviceTypeDTO);
        ServiceTypeResponse serviceTypeResponse = new ServiceTypeResponse();
        serviceTypeResponse.setData(newServiceType);
        return new ResponseEntity<>(serviceTypeResponse, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> deleteServiceType(UUID serviceTypeId) {
        if (serviceTypeService.serviceTypeExist(serviceTypeId.toString())) {
            LOGGER.error(SERVICE_TYPE_NOT_FOUND_ERROR_MSG);
            throw new ResourceNotFoundException(SERVICE_TYPE_NOT_FOUND_ERROR_MSG);
        }

        serviceTypeService.deleteServiceType(serviceTypeId.toString());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<ServiceTypeListResponse> getServiceTypes() {
        List<ServiceTypeDTO> serviceTypeDTOList = serviceTypeService.getCustomers();
        ServiceTypeListResponse serviceTypeListResponse = new ServiceTypeListResponse();
        serviceTypeListResponse.setData(serviceTypeDTOList);
        return new ResponseEntity<>(serviceTypeListResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ServiceTypeResponse> updateServiceType(UUID serviceTypeId, ServiceTypeDTO serviceTypeDTO) {
        if (serviceTypeService.serviceTypeExist(serviceTypeId.toString())) {
            LOGGER.error(SERVICE_TYPE_NOT_FOUND_ERROR_MSG);
            throw new ResourceNotFoundException(SERVICE_TYPE_NOT_FOUND_ERROR_MSG);
        }

        ServiceTypeDTO updatedServiceType = serviceTypeService.updateServiceType(serviceTypeId.toString(),
                serviceTypeDTO);
        ServiceTypeResponse serviceTypeResponse = new ServiceTypeResponse();
        serviceTypeResponse.setData(updatedServiceType);
        return new ResponseEntity<>(serviceTypeResponse, HttpStatus.OK);

    }
}
