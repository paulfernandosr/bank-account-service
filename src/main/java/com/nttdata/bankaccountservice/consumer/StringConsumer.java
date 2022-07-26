package com.nttdata.bankaccountservice.consumer;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import reactor.core.publisher.Flux;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;
import reactor.kafka.receiver.ReceiverRecord;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class StringConsumer {

    public void kafkaReceiver(){
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        ReceiverOptions<String, String> receiverOptions = ReceiverOptions.create(props);
        receiverOptions.subscription(Collections.singleton("reactive-test"));
        KafkaReceiver<String, String> receiver = KafkaReceiver.create(receiverOptions);
        Flux<ReceiverRecord<String, String>> inboundFlux = receiver.receive();
        inboundFlux.doOnNext(record -> System.out.println("Received message: " + record.value())).subscribe();
    }

}
