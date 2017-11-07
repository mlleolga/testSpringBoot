package com.testspringboot.repo;


import com.testspringboot.persistance.BalanceEntity;
import com.testspringboot.persistance.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BalanceRepository extends CrudRepository<BalanceEntity, Long>{

    BalanceEntity findByUser(UserEntity user);
}
