package com.distrise.nostr.nostrgateway.service;

import static java.nio.charset.StandardCharsets.UTF_8;

import com.distrise.nostr.event.Event;
import com.distrise.nostr.json.EndOfStoreEventAdaptor;
import com.distrise.nostr.json.EventMessageAdaptor;
import com.distrise.nostr.json.HexByteStringAdaptor;
import com.distrise.nostr.json.RelayMessageAdaptor;
import com.distrise.nostr.nostrgateway.jpa.EventRepository;
import com.distrise.nostr.nostrgateway.jpa.entity.EventEntity;
import com.distrise.nostr.relay.message.EndOfStoredEvent;
import com.distrise.nostr.relay.message.EventMessage;
import com.distrise.nostr.relay.message.RelayMessage;
import com.google.gson.*;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okio.ByteString;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@Transactional
@RequiredArgsConstructor
public class RelayQueueConsumer implements MessageListener {

  private final Gson gson = new GsonBuilder()
    .registerTypeAdapter(ByteString.class, new HexByteStringAdaptor())
    .registerTypeAdapter(EndOfStoredEvent.class, new EndOfStoreEventAdaptor())
    .registerTypeAdapter(EventMessage.class, new EventMessageAdaptor())
    .registerTypeAdapter(RelayMessage.class, new RelayMessageAdaptor())
    .disableHtmlEscaping()
    .create();

  private final EventRepository eventRepository;

  @Override
  public void onMessage(Message message) {
    final String json = ByteString.of(message.getBody()).string(UTF_8);
    // deserialize
    final JsonArray jsonArray = JsonParser.parseString(json).getAsJsonArray();
    final String messageType = jsonArray.get(0).getAsString();
    switch (messageType) {
      case "EVENT":
        final String subscriptionId = jsonArray.get(1).getAsString();
        final JsonElement jsonElement = jsonArray.get(2);
        final Event event = gson.fromJson(jsonElement, Event.class);
        if (event.verifySign()) {
          eventRepository.save(mapper(subscriptionId, event));
        }
        break;
      case "REQ":
        // do nothing
        break;
      case "EOSE":
        // do nothing
        break;
      default:
        throw new UnsupportedOperationException("Unsupported type: " + messageType);
    }

  }

  private EventEntity mapper(String subscriptionId, Event event) {
    return EventEntity.builder().id(event.id().hex()).createdAt(event.createdAt()).pubkey(event.pubkey().hex())
      .subscription(subscriptionId).content(event.content()).build();
  }

  @Override
  public void containerAckMode(AcknowledgeMode mode) {
    MessageListener.super.containerAckMode(mode);
  }

  @Override
  public boolean isAsyncReplies() {
    return MessageListener.super.isAsyncReplies();
  }

  @Override
  public void onMessageBatch(List<Message> messages) {
    MessageListener.super.onMessageBatch(messages);
  }
}