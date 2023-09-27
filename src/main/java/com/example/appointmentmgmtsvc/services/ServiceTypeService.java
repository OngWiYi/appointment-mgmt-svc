/*
 * Copyright (c) Wi Yi 2023.
 */

package com.example.appointmentmgmtsvc.services;

import com.example.appointmentmgmtsvc.model.ServiceTypeDTO;

import java.util.List;

public interface ServiceTypeService {
    ServiceTypeDTO createServiceType(ServiceTypeDTO serviceTypeDTO);

    boolean serviceTypeExist(String id);

    void deleteServiceType(String id);

    List<ServiceTypeDTO> getCustomers();

    ServiceTypeDTO updateServiceType(String id, ServiceTypeDTO serviceTypeDTO);
}
