package com.vs.kafka.integration.services;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

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

        ListenableFuture<SendResult<String, Greeting>> future =
                kafkaTemplate.send("vvs-topic", message);

        future.addCallback(new ListenableFutureCallback<>() {

            @Override
            public void onSuccess(SendResult<String, Greeting> result) {
                logger.debug("Sent message=[" + message.getMsg() +" " + message.getName()+
                        "] with offset=[" + result.getRecordMetadata().offset() + "]");
            }
            @Override
            public void onFailure(Throwable ex) {
                logger.warn("Unable to send message=["
                        + message + "] due to : " + ex.getMessage());
            }
        });
    }

    @KafkaListener(topics = "vvs-topic", groupId = "foo")
    public void listenGroupFoo(@Payload Greeting message) {
        logger.debug("Received Message in group foo: "+  message.getMsg() +" " + message.getName());
    }

}
