package com.testspringboot.repo;

import com.testspringboot.persistance.HistoryEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryRepository extends CrudRepository<HistoryEntity, Long> {

}
