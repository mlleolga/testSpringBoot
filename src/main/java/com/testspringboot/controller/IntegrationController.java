package com.testspringboot.controller;

import com.testspringboot.Dto.IntegrationRequest;
import com.testspringboot.Dto.IntegrationResponse;
import com.testspringboot.service.IntegrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/integration")
public class IntegrationController {

    @Autowired
    private IntegrationService integrationService;

    @RequestMapping(method = RequestMethod.GET)
    public List<IntegrationResponse> getAllUsersIntegrations(Authentication authResult) {
        return integrationService.getAllUsersIntegrations((String) authResult.getPrincipal());
    }

    @RequestMapping(method = RequestMethod.POST)
    public IntegrationResponse createIntegration(Authentication authResult,@RequestBody IntegrationRequest request){
        return integrationService.createIntegration((String) authResult.getPrincipal(), request);
    }



}
