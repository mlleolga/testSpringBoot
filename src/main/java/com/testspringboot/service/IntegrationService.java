package com.testspringboot.service;

import com.testspringboot.Dto.IntegrationRequest;
import com.testspringboot.Dto.IntegrationResponse;
import com.testspringboot.persistance.IntegrationEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IntegrationService {

    List<IntegrationResponse> getAllUsersIntegrations(String userName);
    IntegrationResponse createIntegration (String userName, IntegrationRequest integrationRequest);
    IntegrationEntity findIntegrationById (Long id);



}
