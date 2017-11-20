package com.testspringboot.service;


import com.testspringboot.Dto.HistoryRequest;

public interface HistoryService {

    void logHistory(HistoryRequest historyRequest);
    void saveHistory(String json);



}
