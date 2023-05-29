package com.distrise.nostr.nostrgateway.config;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableRabbit
@Configuration
@RequiredArgsConstructor
public class RabbitMqConfig {

  private final ConnectionFactory factory;

  @Bean
  SimpleMessageListenerContainer messageListenerContainer() {
    return new SimpleMessageListenerContainer(factory);
  }

}