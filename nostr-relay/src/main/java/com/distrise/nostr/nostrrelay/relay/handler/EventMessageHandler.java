package com.distrise.nostr.nostrrelay.relay.handler;

import com.distrise.nostr.event.Event;
import com.distrise.nostr.nostrrelay.jpa.EventRepository;
import com.distrise.nostr.nostrrelay.jpa.entity.EventEntity;
import com.distrise.nostr.nostrrelay.relay.config.RelayProperties;
import com.distrise.nostr.relay.message.EndOfStoredEvent;
import com.distrise.nostr.relay.message.Notice;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import java.io.IOException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

@Slf4j
@Component
@RequiredArgsConstructor
public class EventMessageHandler {

  // todo - I'm not sure how to created the subscription ID?
  private final RelayProperties properties;

  private final Gson gson;

  private final EventRepository eventRepository;

  @Transactional
  public void handler(WebSocketSession session, JsonElement message) throws IOException {
    final Event event = gson.fromJson(message, Event.class);
    if (!event.verifySign()) {
      session.sendMessage(new TextMessage(gson.toJson(new Notice("signature verification error"))));
    }

    log.info("From pubkey: {}, message: {}", event.pubkey().hex(), event.content());
    eventRepository.save(mapper(event));

    session.sendMessage(new TextMessage(gson.toJson(new EndOfStoredEvent(properties.getSubscriptionId()))));
  }

  private EventEntity mapper(Event source) {
    return EventEntity.builder()
      .id(source.id().hex())
      .pubkey(source.pubkey().hex())
      .sig(source.sig().hex())
      .createdAt(source.createdAt())
      .content(source.content())
      .subscription(properties.getSubscriptionId())
      .build();
  }
}