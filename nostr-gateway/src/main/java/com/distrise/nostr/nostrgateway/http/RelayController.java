package com.distrise.nostr.nostrgateway.http;

import com.distrise.nostr.client.RelayClient;
import com.distrise.nostr.event.Subscription;
import com.distrise.nostr.nostrgateway.http.dto.RelayDto;
import com.distrise.nostr.nostrgateway.service.RelayListener;
import com.distrise.nostr.nostrgateway.service.RabbitMqManager;
import com.distrise.nostr.nostrgateway.service.RelayQueueConsumer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * A simple API for subscribed the Relay
 */
@Slf4j
@RestController
@RequestMapping("/relay")
@RequiredArgsConstructor
public class RelayController {

  private final AmqpTemplate amqpTemplate;

  private final RabbitMqManager rabbitMqManager;

  private final SimpleMessageListenerContainer messageListenerContainer;

  private final RelayQueueConsumer relayQueueConsumer;

  @PostMapping
  @ResponseStatus(code = HttpStatus.CREATED)
  void subscribed(@RequestBody RelayDto relayInfo) {
    // dynamic create RabbitMQ resource
    final Queue queue = rabbitMqManager.createdQueue(relayInfo.getSubscribedId());
    final DirectExchange exchange = rabbitMqManager.createdDirectExchange(relayInfo.getSubscribedId());
    final Binding binding = rabbitMqManager.binding(relayInfo.getSubscribedId(), queue, exchange);

    // dynamic create RabbitMQ consumer
    messageListenerContainer.setMessageListener(relayQueueConsumer);
    messageListenerContainer.addQueues(queue);
    messageListenerContainer.start();

    // create RelayClient
    final RelayListener relayListener = new RelayListener(amqpTemplate, exchange, binding);

    // relay subscribe
    final RelayClient relayClient = RelayClient.builder().url(relayInfo.getUrl()).listener(relayListener).build();
    relayClient.subscribe(new Subscription(relayInfo.getSubscribedId()));
    log.info("Subscribed Relay({}) / {}", relayInfo.getUrl(), relayInfo.getSubscribedId());
  }
}