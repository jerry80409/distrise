package com.distrise.nostr.nostrrelay.relay.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

@Slf4j
@Component
public class RelayHandler implements WebSocketHandler {

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