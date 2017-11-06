package com.testspringboot.repo;

import com.testspringboot.persistance.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {
    List<UserEntity> findAll();
    UserEntity findById(Long id);
    UserEntity findByEmail(String userEmail);
    UserEntity findByUsername(String userName);
}
