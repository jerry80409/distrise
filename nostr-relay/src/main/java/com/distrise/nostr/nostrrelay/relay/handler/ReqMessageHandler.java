package com.distrise.nostr.nostrrelay.relay.handler;

import com.distrise.nostr.crypto.SecKey;
import com.distrise.nostr.event.Event;
import com.distrise.nostr.event.TextContent;
import com.distrise.nostr.nostrrelay.relay.config.RelayProperties;
import com.distrise.nostr.relay.message.EndOfStoredEvent;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import okio.ByteString;
import org.loremipsum.LoremIpsum;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReqMessageHandler implements MessageHandler {

  private static final String FAKE_KEY = "64e38cc7fff2980d919f3c7ab6e5210e6575367dfc5b8a0a94c1ae7093cddcae";

  private final RelayProperties properties;

  private final Gson gson;

  @SneakyThrows
  @Override
  public void handler(WebSocketSession session, String subscriptionId) throws IOException {
    // response Eose
    session.sendMessage(new TextMessage(gson.toJson(new EndOfStoredEvent(subscriptionId))));

    int counter = 1;
    while (counter < 10) {
      final String json = gson.toJson(List.of("EVENT", properties.getSubscriptionId(), mockEvent()));
      session.sendMessage(new TextMessage(json));

      Thread.sleep(3000L);
      counter ++;
    }
  }

  private Event mockEvent() {
    final TextContent message = TextContent.builder().content(LoremIpsum.getWord(10, 20)).build();
    return message.sign(new SecKey(ByteString.decodeHex(FAKE_KEY)));
  }
}