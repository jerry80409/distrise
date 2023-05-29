package com.distrise.nostr.nostrrelay.relay.handler;

import java.io.IOException;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

public interface MessageHandler {

  void handler(WebSocketSession session, String message) throws IOException;
}