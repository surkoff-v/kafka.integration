package com.vs.kafka.integration;

import com.vs.kafka.integration.services.Greeting;
import com.vs.kafka.integration.services.MessagesService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.kafka.listener.MessageListener;

@SpringBootApplication
public class KafkaApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(KafkaApplication.class, args);

		MessagesService messagesService = context.getBean(MessagesService.class);

		messagesService.sendMessage(new Greeting("hi","vova"));

		//context.close();

	}

}
