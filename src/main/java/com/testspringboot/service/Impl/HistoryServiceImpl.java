package com.testspringboot.service.Impl;

import com.testspringboot.Dto.HistoryRequest;
import com.testspringboot.mapper.MapperJsonUtil;
import com.testspringboot.persistance.HistoryEntity;
import com.testspringboot.persistance.HistoryType;
import com.testspringboot.repo.HistoryRepository;
import com.testspringboot.repo.UserRepository;
import com.testspringboot.service.HistoryService;
import com.testspringboot.service.KafkaSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class HistoryServiceImpl implements HistoryService{

    @Lazy
    @Autowired
    private KafkaSender kafkaSender;

    @Autowired
    private HistoryRepository historyRepository;

    @Autowired
    private UserRepository userRepository;


    @Override
    public void logHistory(HistoryRequest historyRequest) {
        System.out.println("STARTED TO KAFKA :" + System.currentTimeMillis());
        kafkaSender.sendMessage(MapperJsonUtil.parseToJson(historyRequest));
        System.out.println("FINISHED TO KAFKA :" + System.currentTimeMillis());
    }

    @Override
    public void saveHistory(String json) {
        historyRepository.save(createHistoryEntity(json));
    }

    private HistoryEntity createHistoryEntity(String json){
        HistoryRequest historyRequest = MapperJsonUtil.parseFromJson(json, HistoryRequest.class);
        HistoryEntity historyEntity = new HistoryEntity();
        historyEntity.setContent(historyRequest.getHistoryContent());
        historyEntity.setHistoryType(HistoryType.valueOf(historyRequest.getHistoryType()));
        historyEntity.setUser(userRepository.findOne(historyRequest.getUserId()));
        return historyEntity;

    }
}
