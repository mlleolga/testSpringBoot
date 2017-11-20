package com.testspringboot.shedulingJob;


import com.testspringboot.Dto.BalanceRequest;
import com.testspringboot.redis.RedisClient;
import com.testspringboot.redis.redisRequest.UserToUpdateBalance;
import com.testspringboot.repo.SmsRepository;
import com.testspringboot.repo.UserRepository;
import com.testspringboot.service.BalanceService;
import org.redisson.api.RSetCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Component
public class ScheduledTasks {
    @Autowired
    private RedisClient redisClient;

    @Autowired
    private SmsRepository smsRepository;

    @Autowired
    private BalanceService balanceService;

    @Autowired
    private UserRepository userRepository;

    private static final String REDIS_KEY = "users_to_update_balance";

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
//The Scheduled annotation defines when a particular method runs
    @Scheduled(cron = "0 */20 * * * *")
    public void reportCurrentTime() {
        RSetCache<UserToUpdateBalance> redisSet = redisClient.getSetCache(REDIS_KEY);

//        for (UserToUpdateBalance userToUpdateBalance: redisSet
//             ) {
//            BigDecimal summ = new BigDecimal(smsRepository.getSumOfSmsRate(userToUpdateBalance.getUserId().toString(), getDateHourAgo() ,new Date(System.currentTimeMillis())));
//            BalanceRequest balanceRequest = new BalanceRequest();
//            balanceRequest.setValueToUpdate(summ.multiply(new BigDecimal(-1)));
//            balanceService.updateBalance(userRepository.findById(userToUpdateBalance.getUserId()).getUsername(), balanceRequest);
//            redisSet.remove(userToUpdateBalance);
//        }
        redisSet.forEach(userToUpdateBalance -> {
            BigDecimal summ = new BigDecimal(smsRepository.getSumOfSmsRate(userToUpdateBalance.getUserId().toString(), getDateHourAgo() ,new Date(System.currentTimeMillis())));
            balanceService.updateBalance(userRepository.findOne(userToUpdateBalance.getUserId()).getUsername(), getBalanceRequest(summ));
            redisSet.remove(userToUpdateBalance);
        });
        log.info("The time is now {}", dateFormat.format(new Date()));
    }

    private BalanceRequest getBalanceRequest(BigDecimal summ) {
        BalanceRequest balanceRequest = new BalanceRequest();
        balanceRequest.setValueToUpdate(summ.multiply(new BigDecimal(-1)));
        return balanceRequest;
    }

    private Date getDateHourAgo(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR_OF_DAY, -1);
        return calendar.getTime();
    }

//        BigDecimal summ = new BigDecimal(smsRepository.getSumOfSmsRate(user.getId().toString(), getDateHourAgo() ,new Date(System.currentTimeMillis())));
//        request.setValueToUpdate(summ);
}