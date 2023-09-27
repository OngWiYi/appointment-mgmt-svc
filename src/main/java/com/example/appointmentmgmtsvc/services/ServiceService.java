/*
 * Copyright (c) Wi Yi 2023. 
 */

package com.example.appointmentmgmtsvc.services;

import com.example.appointmentmgmtsvc.model.ServiceDTO;

import java.util.List;
import java.util.Optional;

public interface ServiceService {

    ServiceDTO createService(ServiceDTO serviceDTO);

    boolean serviceExist(String id);

    void deleteService(String id);

    List<ServiceDTO> getServices(String sort, String order);

    Optional<ServiceDTO> getService(String id);

    ServiceDTO updateService(String id, ServiceDTO serviceDTO);
}
