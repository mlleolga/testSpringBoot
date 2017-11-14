package com.testspringboot.controller;

import com.testspringboot.Dto.BalanceRequest;
import com.testspringboot.Dto.BalanceResponse;
import com.testspringboot.service.BalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/balance")
public class BalanceController {

    @Autowired
    private BalanceService balanceService;

    @RequestMapping(method = RequestMethod.GET)
    public BalanceResponse getBalance(Authentication authResult){
     return   balanceService.getBalance ((String) authResult.getPrincipal());
    }

    @RequestMapping(method = RequestMethod.POST)
    public void updateBalance(Authentication authResult,@RequestBody BalanceRequest balanceRequest){
        balanceService.updateBalance((String) authResult.getPrincipal(), balanceRequest);
    }



}
