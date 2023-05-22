package com.distrise.nostr.nostrgateway;

import com.distrise.nostr.json.HexByteStringAdaptor;
import com.distrise.nostr.json.RelayMessageAdaptor;
import com.distrise.nostr.message.relay.EndOfStoredEvent;
import com.distrise.nostr.message.relay.EventMessage;
import com.distrise.nostr.json.EndOfStoreEventAdaptor;
import com.distrise.nostr.json.EventMessageAdaptor;
import com.distrise.nostr.message.relay.RelayMessage;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okio.ByteString;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class NostrGatewayApplicationTests {

  private final Gson gson = new GsonBuilder()
    .registerTypeAdapter(EndOfStoredEvent.class, new EndOfStoreEventAdaptor())
    .registerTypeAdapter(EventMessage.class, new EventMessageAdaptor())
    .registerTypeAdapter(ByteString.class, new HexByteStringAdaptor())
    .registerTypeAdapter(RelayMessage.class, new RelayMessageAdaptor())
    .disableHtmlEscaping()
    .create();

  @Test
  void eose_serialize_and_deserialize() {
    final String json = "[\"EOSE\",\"12j312n31knkajsndaksndas\"]";
    final EndOfStoredEvent endOfStoredEvent = gson.fromJson(json, EndOfStoredEvent.class);
    final String verifyJson = gson.toJson(endOfStoredEvent);
    Assertions.assertThat(verifyJson).isEqualTo(json);
  }

  @Test
  void event_message_serialize_and_deserialize() {
    final String json = "[\"EVENT\",\"12j312n31knkajsndaksndas\",{\"id\":"
      + "\"9902299eab65254c33488975f483f9f338be39e270f00efe211aea9b3726e0c6\","
      + "\"sig\":\"96542f59a53cf6f06230a53c1ed723bc5183c34244c2c24edb29d360e37de47f474958c54b57b2b5e577780f17ba75b43cf2a080ff274d2034bbe9531c22c1f5\","
      + "\"content\":\"Bought a new laptop for coding. It's super fast!\","
      + "\"created_at\":1684765176,\"kind\": 1,"
      + "\"pubkey\":\"da0b1ceb5138018ab88677ee4b3cbce963de8ae962caf32c6d40cb7307f417ea\","
      + "\"tags\":[]}]";

    final EventMessage eventMessage = gson.fromJson(json, EventMessage.class);
    final String verifyJson = gson.toJson(eventMessage);

    // todo - json serialize 無法按照順序
    System.out.println(verifyJson);
  }

  @Test
  void relay_message_serialize_and_deserialize() {
    final String json1 = "[\"EOSE\",\"12j312n31knkajsndaksndas\"]";
    final String json2 = "[\"EVENT\",\"12j312n31knkajsndaksndas\",{\"id\":"
      + "\"9902299eab65254c33488975f483f9f338be39e270f00efe211aea9b3726e0c6\","
      + "\"sig\":\"96542f59a53cf6f06230a53c1ed723bc5183c34244c2c24edb29d360e37de47f474958c54b57b2b5e577780f17ba75b43cf2a080ff274d2034bbe9531c22c1f5\","
      + "\"content\":\"Bought a new laptop for coding. It's super fast!\","
      + "\"created_at\":1684765176,\"kind\": 1,"
      + "\"pubkey\":\"da0b1ceb5138018ab88677ee4b3cbce963de8ae962caf32c6d40cb7307f417ea\","
      + "\"tags\":[]}]";

    final RelayMessage relayMessage1 = gson.fromJson(json1, RelayMessage.class);
    System.out.println(relayMessage1);

    final RelayMessage relayMessage2 = gson.fromJson(json2, RelayMessage.class);
    System.out.println(relayMessage2);
  }
}