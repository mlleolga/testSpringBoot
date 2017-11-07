package com.testspringboot.Dto;


import com.fasterxml.jackson.annotation.JsonProperty;

public class SmsRequest {
    @JsonProperty("key")
    private String integrationApiKey;
    @JsonProperty("content")
    private String content;

    public String getIntegrationApiKey() {
        return integrationApiKey;
    }

    public void setIntegrationApiKey(String integrationApiKey) {
        this.integrationApiKey = integrationApiKey;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
