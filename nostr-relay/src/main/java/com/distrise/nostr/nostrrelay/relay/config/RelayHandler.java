package com.distrise.nostr.nostrrelay.relay.config;

import com.distrise.nostr.nostrrelay.relay.handler.CloseMessageHandler;
import com.distrise.nostr.nostrrelay.relay.handler.EventMessageHandler;
import com.distrise.nostr.nostrrelay.relay.handler.ReqMessageHandler;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class RelayHandler implements WebSocketHandler {

  private final EventMessageHandler eventMessageHandler;

  private final ReqMessageHandler reqMessageHandler;

  private final CloseMessageHandler closeMessageHandler;

  @Override
  public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    log.info("Websocket connected, session: {}", session.getId());
  }

  @Override
  public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
    log.info("Websocket received {} message: {}", session.getId(), message.getPayload());
    if (!message.getClass().equals(TextMessage.class)) {
      throw new UnsupportedOperationException("Unsupported the websocket message");
    }

    final TextMessage textMessage = (TextMessage) message;

    // https://github.com/google/gson/blob/master/extras/src/main/java/com/google/gson/extras/examples/rawcollections/RawCollectionsExample.java
    final JsonArray jsonArray = JsonParser.parseString(textMessage.getPayload()).getAsJsonArray();
    final String type = jsonArray.get(0).getAsString();

    switch (type) {
      case "EVENT" -> eventMessageHandler.handler(session, jsonArray.get(1));
      case "REQ" -> reqMessageHandler.handler(session, jsonArray.get(1).getAsString());
      case "CLOSE" -> closeMessageHandler.handler(session, jsonArray.get(1).getAsString());
      default -> throw new UnsupportedOperationException("Unsupported type: " + type);
    }
  }

  @Override
  public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
    log.error(String.format("Websocket %s transport error", session.getId()), exception);
    session.close(CloseStatus.NOT_ACCEPTABLE);
  }

  @Override
  public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
    log.warn("Websocket {} closed({})", session.getId(), closeStatus.getReason());
    session.close(CloseStatus.NOT_ACCEPTABLE);
  }

  @Override
  public boolean supportsPartialMessages() {
    return false;
  }
}