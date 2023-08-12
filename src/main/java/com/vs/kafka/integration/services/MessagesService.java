package com.vs.kafka.integration.services;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

@Service
public class MessagesService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private KafkaTemplate<String, Greeting> kafkaTemplate;

    /*   public void sendMessage(String msg) {
           kafkaTemplate.send("vvs-topic", msg);
       }
   */
    public void sendMessage(Greeting message) {

        CompletableFuture<SendResult<String, Greeting>> future = kafkaTemplate.send("vvs-topic", LocalDateTime.now().toString(), message);

        future.whenComplete((result, ex) -> {
                    if (ex == null) {
                        logger.info("Sent message=[" + message.getMsg() + " " + message.getName() +
                                "] with offset=[" + result.getRecordMetadata().offset() + "]");
                    } else {
                        logger.error("Unable to send message=["
                                + message + "] due to : " + ex.getMessage());
                    }


                }
        );
    }

    @KafkaListener(topics = "vvs-topic", groupId = "foo")
    public void listenGroupFoo(@Payload Greeting message
            , @Header(KafkaHeaders.GROUP_ID) String groupId
            , @Header(KafkaHeaders.RECEIVED_KEY) String key
            , @Header(KafkaHeaders.RECEIVED_PARTITION) Integer partition
            , @Header(KafkaHeaders.RECEIVED_TOPIC) String topic
            , @Header(KafkaHeaders.OFFSET) Long offset
            , Acknowledgment ack) {

        logger.debug("Received message " + message.getMsg() + " " + message.getName());
        logger.debug("groupId " + groupId);
        logger.debug("key " + key);
        logger.debug("partition " + partition);
        logger.debug("topic " + topic);
        logger.debug("offset " + offset);

        ack.acknowledge();
    }

}
