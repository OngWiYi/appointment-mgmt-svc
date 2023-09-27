/*
 * Copyright (c) Wi Yi 2023.
 */

package com.example.appointmentmgmtsvc.services;

import com.example.appointmentmgmtsvc.entities.ServiceProvided;
import com.example.appointmentmgmtsvc.entities.ServiceType;
import com.example.appointmentmgmtsvc.model.ServiceDTO;
import com.example.appointmentmgmtsvc.model.ServiceTypeDTO;
import com.example.appointmentmgmtsvc.repositories.ServiceRepository;
import com.example.appointmentmgmtsvc.services.service_impl.ServiceServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ServiceServiceTest {
    private static final String serviceId = "id1";
    private static final String orderAsc = "asc";
    private static final String orderDesc = "desc";
    private static final String sort = "column1";
    @Mock
    private ServiceRepository serviceRepository;
    private ServiceService serviceService;
    private ServiceProvided serviceProvided;
    private ServiceDTO serviceDTO;

    @BeforeEach
    void setUp() {
        this.serviceDTO = new ServiceDTO("service-1", "service-description", new ServiceTypeDTO("type-1", "service-type"), BigDecimal.valueOf(123.00));

        ServiceType serviceType = new ServiceType();
        serviceType.setName("type-1");
        serviceType.setDescription("service-type");

        serviceProvided = new ServiceProvided();
        serviceProvided.setName("service-1");
        serviceProvided.setDescription("service-description");
        serviceProvided.setDetails("service-details");
        serviceProvided.setPrice(BigDecimal.valueOf(123.00));
        serviceProvided.setType(serviceType);

        this.serviceService = new ServiceServiceImpl(this.serviceRepository, new ModelMapper());
    }

    @Test
    void createServiceShouldCallSave() {
        when(serviceRepository.save(any())).thenReturn(serviceProvided);
        serviceService.createService(serviceDTO);
        verify(serviceRepository).save(any());
    }

    @Test
    void serviceExistShouldCallExistsById() {
        serviceService.serviceExist(serviceId);
        verify(serviceRepository).existsById(serviceId);
    }

    @Test
    void deleteServiceShouldCallDeleteById() {
        serviceService.deleteService(serviceId);
        verify(serviceRepository).deleteById(serviceId);
    }

    @Test
    void getServicesShouldCallFindAllWithCorrectSortingOrder() {
        serviceService.getServices(sort, orderAsc);
        verify(serviceRepository).findAll(Sort.by(sort).ascending());
        serviceService.getServices(sort, orderDesc);
        verify(serviceRepository).findAll(Sort.by(sort).descending());
    }

    @Test
    void getServicesShouldThrowIllegalArgumentExceptionWhenSortIsNull() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> serviceService.getServices(null, orderAsc));
    }

    @Test
    void getServicesShouldThrowIllegalArgumentExceptionWhenSortIsEmpty() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> serviceService.getServices("", orderAsc));
    }

    @Test
    void getServicesShouldThrowNullPointerExceptionWhenOrderIsNull() {
        Assertions.assertThrows(NullPointerException.class, () -> serviceService.getServices(sort, null));
    }

    @Test
    void getServiceShouldCallFindById() {
        serviceService.getService(serviceId);
        verify(serviceRepository).findById(serviceId);
    }

    @Test
    void updateServiceShouldCallSave() {
        when(serviceRepository.save(any())).thenReturn(serviceProvided);
        serviceService.updateService(serviceId, serviceDTO);
        verify(serviceRepository).save(any());
    }
}