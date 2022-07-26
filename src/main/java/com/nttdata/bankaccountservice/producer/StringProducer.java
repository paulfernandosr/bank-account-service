package com.nttdata.bankaccountservice.producer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import reactor.core.publisher.Mono;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderOptions;
import reactor.kafka.sender.SenderRecord;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class StringProducer {

    public void generateMessages(String topic, String message) {
        Map<String, Object> producerProps = new HashMap<>();
        producerProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        producerProps.put(ProducerConfig.CLIENT_ID_CONFIG, "nttdata-bootcamp");
        producerProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        producerProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        SenderOptions<String, String> senderOptions = SenderOptions.create(producerProps);
        KafkaSender<String, String> sender =  KafkaSender.create(senderOptions);
        Mono<SenderRecord<String, String, String>> outboundMono = Mono.just(SenderRecord.create(new ProducerRecord<>(topic, message), message));
        sender.send(outboundMono).subscribe();
    }

}
