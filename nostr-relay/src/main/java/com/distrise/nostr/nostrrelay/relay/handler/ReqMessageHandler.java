package com.distrise.nostr.nostrrelay.relay.handler;

import com.distrise.nostr.nostrrelay.relay.handler.MessageHandler;
import com.distrise.nostr.relay.message.EndOfStoredEvent;
import com.distrise.nostr.relay.message.Notice;
import com.google.gson.Gson;
import com.rabbitmq.client.UnblockedCallback;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.loremipsum.LoremIpsum;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReqMessageHandler implements MessageHandler {

  private final Gson gson;

  @SneakyThrows
  @Override
  public void handler(WebSocketSession session, String subscriptionId) throws IOException {
    // response Eose
    session.sendMessage(new TextMessage(gson.toJson(new EndOfStoredEvent(subscriptionId))));

    int counter = 1;
    while (counter < 10) {
      final String json = gson.toJson(List.of("NOTICE", LoremIpsum.getWord(2, 10)));
      session.sendMessage(new TextMessage(json));

      Thread.sleep(3000L);
      counter ++;
    }
  }
}