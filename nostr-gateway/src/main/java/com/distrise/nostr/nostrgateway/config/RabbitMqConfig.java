package com.distrise.nostr.nostrgateway.config;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableRabbit
@Configuration
public class RabbitMqConfig {

  public static final String RELAY_QUEUE = "RELAY_QUEUE";

  public static final String RELAY_EXCHANGE = "RELAY_EXCHANGE";

  public static final String RELAY_BINDING = "RELAY_EXCHANGE_ROUTING";

  // https://docs.spring.io/spring-amqp/reference/html/#quick-tour
  // https://blog.csdn.net/qq_35387940/article/details/100514134
  @Bean
  Queue relayQueue() {
    return new Queue(RELAY_QUEUE, true, true, false);
  }

  /**
   * I choose direct because it's pure and simple.
   * And I also consider the
   */
  @Bean
  DirectExchange relayExchange() {
    return new DirectExchange(RELAY_EXCHANGE, true, false);
  }

  /**
   * binding RabbitMQ exchange with queue.
   */
  @Bean
  Binding bindingExchangeWithQueue() {
    return BindingBuilder.bind(relayQueue()).to(relayExchange()).with(RELAY_BINDING);
  }
}