package com.testspringboot.service;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Arrays;
import java.util.Properties;

@Component
public class KafkaSender {
    @Autowired
            private HistoryService historyService;

    Producer<String, String> producer;
    KafkaConsumer<String, String> consumer;
    @PostConstruct
    private void init(){
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        this.producer = new KafkaProducer<String, String>(props);
        initConsumer();
//        consume();
    }

    public void sendMessage(String s){
        producer.send(new ProducerRecord<String, String>("javainuse-topic", s));
    }

    private void initConsumer(){
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "test");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        this.consumer =  new KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList("javainuse-topic"));
    }

    @PreDestroy
    private  void  destroyProducer(){
        this.producer.close();
    }

    @Async
    public void consume(){
    while (true) {
        ConsumerRecords<String, String> records = consumer.poll(1000);
        records.forEach(stringStringConsumerRecord -> {
            System.out.println("LISTENED FROM KAFKA :" + System.currentTimeMillis());
//            System.out.println(stringStringConsumerRecord.value());
        historyService.saveHistory(stringStringConsumerRecord.value());

            System.out.println("SAVE TO DB AFTER KAFKA :" + System.currentTimeMillis());
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    }

}
