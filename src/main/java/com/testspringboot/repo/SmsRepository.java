package com.testspringboot.repo;


import com.testspringboot.persistance.SentMessages;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SmsRepository extends CrudRepository<SentMessages, Long>{


}
