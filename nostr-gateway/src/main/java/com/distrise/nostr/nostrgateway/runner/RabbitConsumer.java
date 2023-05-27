package com.distrise.nostr.nostrgateway.runner;

import com.distrise.nostr.event.Event;
import com.distrise.nostr.json.EndOfStoreEventAdaptor;
import com.distrise.nostr.json.EventMessageAdaptor;
import com.distrise.nostr.json.HexByteStringAdaptor;
import com.distrise.nostr.json.RelayMessageAdaptor;
import com.distrise.nostr.relay.message.EndOfStoredEvent;
import com.distrise.nostr.relay.message.EventMessage;
import com.distrise.nostr.relay.message.RelayMessage;
import com.distrise.nostr.nostrgateway.config.RabbitMqConfig;
import com.distrise.nostr.nostrgateway.jpa.EventRepository;
import com.distrise.nostr.nostrgateway.jpa.entity.EventEntity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import okio.ByteString;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@Transactional
@RabbitListener(queues = RabbitMqConfig.RELAY_QUEUE)
class RabbitConsumer {

  private final Gson gson = new GsonBuilder()
    .registerTypeAdapter(EndOfStoredEvent.class, new EndOfStoreEventAdaptor())
    .registerTypeAdapter(EventMessage.class, new EventMessageAdaptor())
    .registerTypeAdapter(ByteString.class, new HexByteStringAdaptor())
    .registerTypeAdapter(RelayMessage.class, new RelayMessageAdaptor())
    .disableHtmlEscaping()
    .create();

  private final EventRepository eventRepository;

  RabbitConsumer(EventRepository eventRepository) {
    this.eventRepository = eventRepository;
  }

  @RabbitHandler
  public void received(String json) {
    log.info("Relay Queue consumer event: {}", json);

    // deserialize
    final RelayMessage relayMessage = gson.fromJson(json, RelayMessage.class);


    // hard coding
    if (EventMessage.class.getCanonicalName().equals(relayMessage.getClass().getCanonicalName())) {
      final EventMessage eventMessage = (EventMessage) relayMessage;
      final Event event = eventMessage.event();

      if (!event.verifySign()) {
        // todo - consider emit other message queue
        log.warn("Wrong signature with from subscription: {}, message id: {}, content: {}",
          eventMessage.subscriptionId(), event.id().hex(), event.content());
      }

      // persisted
      eventRepository.save(mapper(eventMessage.subscriptionId(), event));
    }
  }

  private EventEntity mapper(String subscriptionId, Event event) {
    return EventEntity.builder().id(event.id().hex()).createdAt(event.createdAt()).pubkey(event.pubkey().hex())
      .subscription(subscriptionId).content(event.content()).build();
  }
}