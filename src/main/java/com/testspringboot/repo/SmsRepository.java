package com.testspringboot.repo;


import com.testspringboot.Dto.BalanceRequest;
import com.testspringboot.persistance.SentMessages;
import com.testspringboot.persistance.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

@Repository
public interface SmsRepository extends CrudRepository<SentMessages, Long>{

@Query(value = "select sum(s.rate_message) from sent_mes s  where s.user_id = :userId and (s.created_date >= :from and s.created_date <= :to )", nativeQuery = true)
    double getSumOfSmsRate (@Param("userId") String userName, @Param("from") Date from, @Param("to") Date to);
    }

