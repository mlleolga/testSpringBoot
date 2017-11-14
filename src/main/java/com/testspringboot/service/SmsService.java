package com.testspringboot.service;

import com.testspringboot.Dto.SmsRequest;

public interface SmsService {
    void saveMessage(String userName, SmsRequest request);
}
