package com.distrise.nostr.nostrgateway.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.stereotype.Service;

// tood - checked existing
/**
 * Dynamic created RabbitMQ resource.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RabbitMqManager {

  private final AmqpAdmin amqpAdmin;

  // private final SimpleMessageListenerContainer container;


  /**
   * Binding Queue to Topic Exchange.
   *
   * @param routing
   * @param queue
   * @param exchange
   * @return Binding
   */
  public Binding binding(String routing, Queue queue, TopicExchange exchange) {
    final Binding binding = BindingBuilder.bind(queue).to(exchange).with(routing);
    amqpAdmin.declareBinding(binding);
    return binding;
  }

  /**
   * Binding Queue to Direct Exchange.
   *
   * @param routing
   * @param queue
   * @param exchange
   * @return Binding
   */
  public Binding binding(String routing, Queue queue, DirectExchange exchange) {
    final Binding binding = BindingBuilder.bind(queue).to(exchange).with(routing);
    amqpAdmin.declareBinding(binding);
    return binding;
  }

  /**
   * Created a Topic Exchange
   *
   * @param exhcnageName
   * @return Topic Exchange
   */
  public TopicExchange createdTopicExchange(String exhcnageName) {
    return createdTopicExchange(exhcnageName, true, false);
  }

  /**
   * Created a Topic Exchange
   *
   * @param exchangeName
   * @param durable
   * @param autoDelete
   * @return Topic Exchange
   */
  public TopicExchange createdTopicExchange(String exchangeName, boolean durable, boolean autoDelete) {
    final TopicExchange exchange = new TopicExchange(exchangeName, durable, autoDelete);
    amqpAdmin.declareExchange(exchange);
    return exchange;
  }

  /**
   * Created a Direct Exchange
   *
   * @param exchangeName
   * @return
   */
  public DirectExchange createdDirectExchange(String exchangeName) {
    return createdDirectExchange(exchangeName, true, false);
  }

  /**
   * Created a Direct Exchange
   *
   * @param exchangeName
   * @param durable
   * @param autoDelete
   * @return DirectExchange
   */
  public DirectExchange createdDirectExchange(String exchangeName, boolean durable, boolean autoDelete) {
    final DirectExchange exchange = new DirectExchange(exchangeName, durable, autoDelete);
    amqpAdmin.declareExchange(exchange);
    return exchange;
  }

  /**
   * Created a Queue
   *
   * @param queueName
   * @return Queue
   */
  public Queue createdQueue(String queueName) {
    return createdQueue(queueName, true, false, false);
  }

  /**
   * Created a Queue
   *
   * @param queueName
   * @param durable
   * @param exclusive
   * @param autoDelete
   * @return Queue
   */
  public Queue createdQueue(String queueName, boolean durable, boolean exclusive, boolean autoDelete) {
    final Queue queue = new Queue(queueName, durable, exclusive, autoDelete);
    amqpAdmin.declareQueue(queue);
    return queue;
  }

}