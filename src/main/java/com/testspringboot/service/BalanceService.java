package com.testspringboot.service;


import com.testspringboot.Dto.BalanceRequest;
import com.testspringboot.Dto.BalanceResponse;
import org.springframework.stereotype.Service;

@Service
public interface BalanceService {

    BalanceResponse getBalance(String userName);
    void updateBalance(String userName, BalanceRequest request);
}
