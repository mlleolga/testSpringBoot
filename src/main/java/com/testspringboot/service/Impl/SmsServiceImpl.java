package com.testspringboot.service.Impl;

import com.testspringboot.Dto.SmsRequest;
import com.testspringboot.persistance.IntegrationEntity;
import com.testspringboot.persistance.SentMessages;
import com.testspringboot.persistance.UserEntity;
import com.testspringboot.redis.RedisClient;
import com.testspringboot.redis.redisRequest.UserToUpdateBalance;
import com.testspringboot.repo.SmsRepository;
import com.testspringboot.repo.UserRepository;
import com.testspringboot.service.SmsService;
import org.redisson.api.RSetCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;
import java.util.Random;

@Service
public class SmsServiceImpl implements SmsService {
    @Autowired
    private SmsRepository smsRepository;

    @Autowired
    private RedisClient redisClient;

    @Autowired
    private UserRepository userRepository;

    private static final String REDIS_KEY = "users_to_update_balance";

    @Override
    public void saveMessage(String userName, SmsRequest request) {
        /*Save mesaage into DB*/
        SentMessages sentMessages = new SentMessages();
        UserEntity user = userRepository.findByUsername(userName);
        validateIntegration(user, request.getIntegrationApiKey());
        sentMessages.setContent(request.getContent());
        sentMessages.setIntegrationApiKey(request.getIntegrationApiKey());
        sentMessages.setCreatedDate(new Date(System.currentTimeMillis()));
        sentMessages.setRateMessage(rateSms());
        sentMessages.setUserId(user.getId());
        smsRepository.save(sentMessages);

        /*Save sms user into Redis*/

        RSetCache<UserToUpdateBalance> redisSet = redisClient.getSetCache(REDIS_KEY);
        UserToUpdateBalance userToUpdateBalance = new UserToUpdateBalance();
        userToUpdateBalance.setIntegrationApiKey(request.getIntegrationApiKey());
        userToUpdateBalance.setUserId(user.getId());
        redisSet.add(userToUpdateBalance);

    //    smsRepository.getSumOfSmsRate(user.getId().toString(), getDateHourAgo() ,new Date(System.currentTimeMillis()));
    }

    private void validateIntegration(UserEntity user, String apiKey){
       Optional<IntegrationEntity> optional = user.getIntegrationEntities().stream().filter(i -> {
            if (i.getApiKey().equals(apiKey)){
                return true;
            };
            return false;
        }).findAny();
        if (optional.isPresent()) {} else {throw new RuntimeException("wrong integration ApiKey");}
    }

    private BigDecimal rateSms() {
        Random r = new Random();
        double randomValue = 0.001 + (0.25 - 0.001) * r.nextDouble();
        return new BigDecimal(randomValue);
    }
}
