package com.testspringboot.controller;

import com.testspringboot.service.KafkaSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/javainuse-kafka")
public class ApacheKafkaWebController {
    @Autowired
    KafkaSender kafkaSender;

    @RequestMapping(method = RequestMethod.GET)
    public String producer(@RequestParam("message") String message) {
        kafkaSender.sendMessage(message);

        return "Message sent to the Kafka Topic java_in_use_topic Successfully";
    }
}
