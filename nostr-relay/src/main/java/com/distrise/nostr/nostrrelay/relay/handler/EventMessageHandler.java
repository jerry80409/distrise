package com.distrise.nostr.nostrrelay.relay.handler;

import com.distrise.nostr.event.Event;
import com.distrise.nostr.relay.message.EventMessage;
import com.distrise.nostr.relay.message.RelayMessage;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

@Slf4j
@Component
@RequiredArgsConstructor
public class EventMessageHandler {

  private final Gson gson;

  public void handler(WebSocketSession session, JsonElement message) throws IOException {
    final Event event = gson.fromJson(message, Event.class);
    if (!event.verifySign()) {
      throw new RuntimeException("Sign verify fail");
    }

    // todo - subscribed others
    log.info("From pubkey: {}, message: {}", event.pubkey().hex(), event.content());
  }
}