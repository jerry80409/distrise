package com.distrise.nostr.nostrrelay.relay.handler;

import com.distrise.nostr.event.Event;
import com.distrise.nostr.nostrrelay.jpa.EventRepository;
import com.distrise.nostr.nostrrelay.jpa.entity.EventEntity;
import com.distrise.nostr.relay.message.EventMessage;
import com.distrise.nostr.relay.message.RelayMessage;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okio.ByteString;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

@Slf4j
@Component
@RequiredArgsConstructor
public class EventMessageHandler {

  // todo - I'm not sure how to created the subscription ID?
  private static final String SUBSCRIPTION_ID = UUID.randomUUID().toString();

  private final Gson gson;

  private final EventRepository eventRepository;

  @Transactional
  public void handler(WebSocketSession session, JsonElement message) throws IOException {
    final Event event = gson.fromJson(message, Event.class);
    if (!event.verifySign()) {
      throw new UnsupportedOperationException("Sign verify fail");
    }

    log.info("From pubkey: {}, message: {}", event.pubkey().hex(), event.content());
    eventRepository.save(mapper(event));
  }

  private EventEntity mapper(Event source) {
    return EventEntity.builder()
      .id(source.id().hex())
      .pubkey(source.pubkey().hex())
      .sig(source.sig().hex())
      .createdAt(source.createdAt())
      .content(source.content())
      .subscription(SUBSCRIPTION_ID)
      .build();
  }
}