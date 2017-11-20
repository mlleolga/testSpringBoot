package com.testspringboot.service.Impl;

import com.testspringboot.Dto.BalanceRequest;
import com.testspringboot.Dto.BalanceResponse;
import com.testspringboot.Dto.HistoryRequest;
import com.testspringboot.persistance.BalanceEntity;
import com.testspringboot.persistance.HistoryType;
import com.testspringboot.persistance.UserEntity;
import com.testspringboot.repo.BalanceRepository;
import com.testspringboot.repo.SmsRepository;
import com.testspringboot.repo.UserRepository;
import com.testspringboot.service.BalanceService;
import com.testspringboot.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.Date;

@Service
public class BalanceServiceImpl implements BalanceService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BalanceRepository balanceRepository;

    @Autowired
    private SmsRepository smsRepository;

    @Autowired
    private HistoryService historyService;

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
        historyService.logHistory(createHistoryRequest(balance, request));
    }

    private BalanceResponse getBalanceResponse(UserEntity user) {
        BalanceResponse balanceResponse = new BalanceResponse();
        balanceResponse.setCurrentBalance(user.getBalance().getBalance());
        balanceResponse.setUserId(user.getEmail());
        balanceResponse.setLastUpdatedDate(user.getBalance().getModifiedDate());
        return balanceResponse;
    }

    private HistoryRequest createHistoryRequest(BalanceEntity balanceEntity, BalanceRequest balanceRequest){
        HistoryRequest historyRequest = new HistoryRequest();
        historyRequest.setUserId(balanceEntity.getUser().getId());
        historyRequest.setHistoryType(HistoryType.UPDATE_BALANCE.name());
        DecimalFormat df = new DecimalFormat("#.##");
        historyRequest.setHistoryContent(String.format("balance was changed on sum = " + balanceRequest.getValueToUpdate()
                + ", current balance = " + df.format(balanceEntity.getBalance())));
        return historyRequest;
    }


}
