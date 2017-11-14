package com.testspringboot.repo;

import com.testspringboot.persistance.IntegrationEntity;
import com.testspringboot.persistance.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IntegrationRepository extends CrudRepository<IntegrationEntity, Long>{
    List<IntegrationEntity> findAllByUserEntity(UserEntity user);
    IntegrationEntity findById(Long id);
}
