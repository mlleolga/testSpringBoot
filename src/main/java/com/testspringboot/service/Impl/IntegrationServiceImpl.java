package com.testspringboot.service.Impl;

import com.testspringboot.Dto.HistoryRequest;
import com.testspringboot.Dto.IntegrationRequest;
import com.testspringboot.Dto.IntegrationResponse;
import com.testspringboot.persistance.HistoryType;
import com.testspringboot.persistance.IntegrationEntity;
import com.testspringboot.persistance.IntegrationType;
import com.testspringboot.persistance.UserEntity;
import com.testspringboot.repo.IntegrationRepository;
import com.testspringboot.repo.UserRepository;
import com.testspringboot.service.HistoryService;
import com.testspringboot.service.IntegrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class IntegrationServiceImpl implements IntegrationService {
    @Autowired
    private IntegrationRepository integrationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HistoryService historyService;

    @Override
    public IntegrationResponse createIntegration(String userName, IntegrationRequest integrationRequest) {
        UserEntity user = userRepository.findByUsername(userName);
        IntegrationEntity integrationEntity = new IntegrationEntity();
        integrationEntity.setApiKey(generateApiKey(integrationRequest.getName()));
        integrationEntity.setUserEntity(user);
        integrationEntity.setIntegrationType(IntegrationType.valueOf(integrationRequest.getTypeOfIntegration()));
        integrationEntity.setName(integrationRequest.getName());
        integrationEntity = integrationRepository.save(integrationEntity);
        historyService.logHistory(createHistoryRequest(integrationEntity, integrationRequest));
        return getIntegrationResponse(integrationEntity);

    }

    @Override
    public List<IntegrationResponse> getAllUsersIntegrations(String userName) {
        UserEntity user = userRepository.findByUsername(userName);
        List<IntegrationEntity> integrations = integrationRepository.findAllByUserEntity(user);

//        List<IntegrationResponse> result = new ArrayList<>();
//        integrations.forEach(integration -> {
//            IntegrationResponse response = new IntegrationResponse();
//            response.setApiKey(integration.getApiKey());
//            response.setId(String.valueOf(integration.getId()));
//            response.setUserId(user.getEmail());
//            result.add(response);
//        });
        return integrations.stream()
                .map(this::getIntegrationResponse)
                .collect(Collectors.toList());
    }

    private IntegrationResponse getIntegrationResponse(IntegrationEntity integration) {
        IntegrationResponse response = new IntegrationResponse();
        response.setApiKey(integration.getApiKey());
        response.setId(String.valueOf(integration.getId()));
        response.setUserId(integration.getUserEntity().getEmail());
        return response;
    }


    @Override
    public IntegrationEntity findIntegrationById(Long id) {
        return null;
    }

    private String generateApiKey(String name){
        try{
            byte[] bytes = name.getBytes("UTF-8");
            UUID uuid = UUID.nameUUIDFromBytes(bytes);
            return uuid.toString();
        } catch (Exception e){

        }
       return null;

    }

    private HistoryRequest createHistoryRequest(IntegrationEntity integrationEntity, IntegrationRequest integrationRequest){
        HistoryRequest historyRequest = new HistoryRequest();
        historyRequest.setUserId(integrationEntity.getUserEntity().getId());
        historyRequest.setHistoryType(HistoryType.CREATE_INTEGRATION.name());
        historyRequest.setHistoryContent("created integration: " + integrationRequest.getTypeOfIntegration() + ": " + integrationRequest.getName()
                + " for user " + integrationEntity.getUserEntity().getUsername());
        return historyRequest;
    }

}

