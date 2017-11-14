package com.testspringboot.controller;

import com.testspringboot.Dto.SmsRequest;
import com.testspringboot.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sms")
public class SmsController {
    @Autowired
    private SmsService smsService;

    @RequestMapping(method = RequestMethod.POST)
    public void sendSms(Authentication authResult, @RequestBody SmsRequest request){
        smsService.saveMessage((String) authResult.getPrincipal(), request);
    }
}
