package com.distrise.nostr.nostrrelay.relay.handler;

import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;

@Slf4j
@Component
@RequiredArgsConstructor
public class CloseMessageHandler implements MessageHandler {

  @Override
  public void handler(WebSocketSession session, String subscriptionId) throws IOException {
    session.close(CloseStatus.NORMAL);
  }
}