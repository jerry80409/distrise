package com.distrise.nostr.example;

import com.distrise.nostr.client.RelayClient;
import com.distrise.nostr.crypto.SecKey;
import com.distrise.nostr.event.Event;
import com.distrise.nostr.event.TextContent;
import okio.ByteString;

public class ExampleSendMessage {

  private static final String URL = "wss://relay.nekolicio.us";

  public static void main(String[] args) {
    final RelayClient client = RelayClient.builder().url(URL).build();

    final TextContent content = TextContent.builder().content("=== Jerry test 2 ===").build();
    final SecKey key = new SecKey(ByteString.decodeHex("64e38cc7fff2980d919f3c7ab6e5210e6575367dfc5b8a0a94c1ae7093cddcae"));
    final Event event = content.sign(key);

    client.send(event);
  }
}