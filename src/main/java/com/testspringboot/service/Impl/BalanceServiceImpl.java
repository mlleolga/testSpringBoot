package com.testspringboot.service.Impl;

import com.testspringboot.Dto.BalanceRequest;
import com.testspringboot.Dto.BalanceResponse;
import com.testspringboot.persistance.BalanceEntity;
import com.testspringboot.persistance.UserEntity;
import com.testspringboot.repo.BalanceRepository;
import com.testspringboot.repo.SmsRepository;
import com.testspringboot.repo.UserRepository;
import com.testspringboot.service.BalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class BalanceServiceImpl implements BalanceService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BalanceRepository balanceRepository;

    @Autowired
    private SmsRepository smsRepository;

    @Override
    public BalanceResponse getBalance(String userName) {
        return getBalanceResponse(userRepository.findByUsername(userName));
    }

    @Override
    public void updateBalance(String userName, BalanceRequest request) {
        saveBalance(userRepository.findByUsername(userName), request);
    }

    private void saveBalance(UserEntity user, BalanceRequest request) {
//        BigDecimal summ = new BigDecimal(smsRepository.getSumOfSmsRate(user.getId().toString(), getDateHourAgo() ,new Date(System.currentTimeMillis())));
//        request.setValueToUpdate(summ);
        BalanceEntity balance = user.getBalance();
//        balance.setBalance(balance.getBalance().subtract(summ));
        balance.setBalance(balance.getBalance().add(request.getValueToUpdate()));
        balance.setModifiedDate(new Date(System.currentTimeMillis()));
        balanceRepository.save(balance);
    }

    private BalanceResponse getBalanceResponse(UserEntity user) {
        BalanceResponse balanceResponse = new BalanceResponse();
        balanceResponse.setCurrentBalance(user.getBalance().getBalance());
        balanceResponse.setUserId(user.getEmail());
        balanceResponse.setLastUpdatedDate(user.getBalance().getModifiedDate());
        return balanceResponse;
    }


}
