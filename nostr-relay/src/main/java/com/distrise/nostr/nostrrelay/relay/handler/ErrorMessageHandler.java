package com.distrise.nostr.nostrrelay.relay.handler;

import com.distrise.nostr.relay.message.Notice;
import com.google.gson.Gson;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

@Slf4j
@Component
@RequiredArgsConstructor
public class ErrorMessageHandler implements MessageHandler {

  private final Gson gson;

  @Override
  public void handler(WebSocketSession session, String message) throws IOException {
    session.sendMessage(new TextMessage(gson.toJson(new Notice(message))));
  }
}