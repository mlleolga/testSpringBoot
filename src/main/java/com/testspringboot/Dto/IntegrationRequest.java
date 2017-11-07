package com.testspringboot.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IntegrationRequest {

    @JsonProperty("name")
    private String name;
    @JsonProperty("type")
    private String typeOfIntegration;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTypeOfIntegration() {
        return typeOfIntegration;
    }

    public void setTypeOfIntegration(String typeOfIntegration) {
        this.typeOfIntegration = typeOfIntegration;
    }
}
