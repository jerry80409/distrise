package com.distrise.nostr.json;

import static org.junit.jupiter.api.Assertions.*;

import com.distrise.nostr.event.Event;
import com.distrise.nostr.relay.message.EndOfStoredEvent;
import com.distrise.nostr.relay.message.EventMessage;
import com.distrise.nostr.relay.message.RelayMessage;
import com.distrise.nostr.relay.message.RequestEvent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.List;
import okio.ByteString;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class RequestEventAdaptorTest {

  private final Gson gson = new GsonBuilder()
    .registerTypeAdapter(ByteString.class, new HexByteStringAdaptor())
    .registerTypeAdapter(EndOfStoredEvent.class, new EndOfStoreEventAdaptor())
    .registerTypeAdapter(EventMessage.class, new EventMessageAdaptor())
    .registerTypeAdapter(RequestEvent.class, new RequestEventAdaptor())
    .registerTypeAdapter(RelayMessage.class, new RelayMessageAdaptor())
    .create();

  @Test
  void eose_serialize_and_deserialize() {
    final String json = "[\"EOSE\",\"12j312n31knkajsndaksndas\"]";
    final EndOfStoredEvent endOfStoredEvent = gson.fromJson(json, EndOfStoredEvent.class);
    final String verifyJson = gson.toJson(endOfStoredEvent);
    Assertions.assertThat(verifyJson).isEqualTo(json);
  }

  @Test
  void event_message_serialize() {
    final String json = "[\"EVENT\",\"12j312n31knkajsndaksndas\",{\"id\":"
      + "\"9902299eab65254c33488975f483f9f338be39e270f00efe211aea9b3726e0c6\","
      + "\"sig\":\"96542f59a53cf6f06230a53c1ed723bc5183c34244c2c24edb29d360e37de47f474958c54b57b2b5e577780f17ba75b43cf2a080ff274d2034bbe9531c22c1f5\","
      + "\"content\":\"Bought a new laptop for coding. It's super fast!\","
      + "\"created_at\":1684765176,\"kind\": 1,"
      + "\"pubkey\":\"da0b1ceb5138018ab88677ee4b3cbce963de8ae962caf32c6d40cb7307f417ea\","
      + "\"tags\":[]}]";

    final EventMessage eventMessage = gson.fromJson(json, EventMessage.class);
    Assertions.assertThat(eventMessage)
      .hasFieldOrPropertyWithValue("subscriptionId", "12j312n31knkajsndaksndas")
      .hasFieldOrPropertyWithValue("event", Event.builder()
        .id(ByteString.decodeHex("9902299eab65254c33488975f483f9f338be39e270f00efe211aea9b3726e0c6"))
        .sig(ByteString.decodeHex("96542f59a53cf6f06230a53c1ed723bc5183c34244c2c24edb29d360e37de47f474958c54b57b2b5e577780f17ba75b43cf2a080ff274d2034bbe9531c22c1f5"))
        .content("Bought a new laptop for coding. It's super fast!")
        .createdAt(1_684_765_176L).kind(1)
        .pubkey(ByteString.decodeHex("da0b1ceb5138018ab88677ee4b3cbce963de8ae962caf32c6d40cb7307f417ea"))
        .tags(List.of())
        .build());
  }

  @Test
  void event_message_deserialize() {
    final Event event = Event.builder()
      .id(ByteString.decodeHex("9902299eab65254c33488975f483f9f338be39e270f00efe211aea9b3726e0c6")).sig(
        ByteString.decodeHex(
          "96542f59a53cf6f06230a53c1ed723bc5183c34244c2c24edb29d360e37de47f474958c54b57b2b5e577780f17ba75b43cf2a080ff274d2034bbe9531c22c1f5"))
      .content("Bought a new laptop for coding. It's super fast!").createdAt(1_684_765_176L).kind(1)
      .pubkey(ByteString.decodeHex("da0b1ceb5138018ab88677ee4b3cbce963de8ae962caf32c6d40cb7307f417ea")).tags(List.of())
      .build();
    final String json = gson.toJson(event);
    Assertions.assertThat(json).isEqualTo("{\"id\":\"9902299eab65254c33488975f483f9f338be39e270f00efe211aea9b3726e0c6"
      + "\",\"pubkey\":\"da0b1ceb5138018ab88677ee4b3cbce963de8ae962caf32c6d40cb7307f417ea\","
      + "\"created_at\":1684765176,\"kind\":1,\"tags\":[],"
      + "\"content\":\"Bought a new laptop for coding. It\\u0027s super fast!\","
      + "\"sig\":\"96542f59a53cf6f06230a53c1ed723bc5183c34244c2c24edb29d360e37de47f474958c54b57b2b5e577780f17ba75b43cf2a080ff274d2034bbe9531c22c1f5\"}");
  }

  @Test
  void relay_message_deserialize() {
    final String json = "[\"EVENT\",\"12j312n31knkajsndaksndas\",{\"id\":"
      + "\"9902299eab65254c33488975f483f9f338be39e270f00efe211aea9b3726e0c6\","
      + "\"sig\":\"96542f59a53cf6f06230a53c1ed723bc5183c34244c2c24edb29d360e37de47f474958c54b57b2b5e577780f17ba75b43cf2a080ff274d2034bbe9531c22c1f5\","
      + "\"content\":\"Bought a new laptop for coding. It's super fast!\","
      + "\"created_at\":1684765176,\"kind\": 1,"
      + "\"pubkey\":\"da0b1ceb5138018ab88677ee4b3cbce963de8ae962caf32c6d40cb7307f417ea\","
      + "\"tags\":[]}]";

    final RelayMessage eventMessage = gson.fromJson(json, RelayMessage.class);
    Assertions.assertThat(eventMessage).isInstanceOf(EventMessage.class);
  }
}