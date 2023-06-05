package com.distrise.nostr.nostrrelay.relay.handler;

import com.distrise.nostr.event.Event;
import com.distrise.nostr.nostrrelay.jpa.EventRepository;
import com.distrise.nostr.nostrrelay.relay.exception.RelayException;
import com.distrise.nostr.relay.message.EndOfStoredEvent;
import com.distrise.nostr.relay.message.EventMessage;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.List;
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
public class ReqMessageHandler implements MessageHandler {

  private final Gson gson;

  private final EventRepository eventRepository;

  @Transactional(readOnly = true)
  @Override
  public void handler(WebSocketSession session, String subscriptionId) throws IOException {
    // response Eose
    session.sendMessage(new TextMessage(gson.toJson(new EndOfStoredEvent(subscriptionId))));

    // streaming select and reply
    eventRepository.findBySubscription(subscriptionId).forEach(event -> {
      final Event relayEvent = Event.builder().id(ByteString.decodeHex(event.getId()))
        .pubkey(ByteString.decodeHex(event.getPubkey())).sig(ByteString.decodeHex(event.getSig()))
        .createdAt(event.getCreatedAt()).content(event.getContent()).kind(1).tags(List.of()).build();

      // reply format ['EVENT', 'subscription', {event payload}]
      final String json = gson.toJson(EventMessage.builder().subscriptionId(subscriptionId)
        .event(relayEvent).build());

      try {
        // reply to client and sleep 3's
        session.sendMessage(new TextMessage(json));
        Thread.sleep(3000L);

      } catch (InterruptedException | IOException e) {
        throw new RelayException(e);
      }
    });
  }
}