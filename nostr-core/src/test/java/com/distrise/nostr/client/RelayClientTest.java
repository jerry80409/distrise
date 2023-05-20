package com.distrise.nostr.client;

import com.distrise.nostr.event.Event;
import java.util.List;
import org.junit.jupiter.api.Test;

class RelayClientTest {

  private static final String URL = "wss://relay.nekolicio.us";

  /**
   * ["EVENT", "12j312n31knkajsndaksndas",
   * {"id": "c373437c7b827f5180a2af75513ba972edbfa4e61b5204c11cba08e0be3b427e",
   * "sig": "820c60c5c749d72aa091bcc94ada63aa2cb1d14dbfb49384b32ff9b4c0c5d17753bcd3df2e30d2e2d4a1481cff2a82d06b8826517afd06196412733a6e9f7fda",
   * "content": "Getting into robotics. Any beginners tips?",
   * "created_at": 1684578866,
   * "kind": 1,
   * "pubkey": "f2a18190fa309dde4dd8095f5f0fc1d19c431236e6815b51f19992283c816c88",
   * "tags": []}]
   */
  @Test
  void test() {
    final RelayClient client = RelayClient.builder().url(URL).build();
    final Event event = Event.builder()
      .id("c373437c7b827f5180a2af75513ba972edbfa4e61b5204c11cba08e0be3b427e")
      .sig("820c60c5c749d72aa091bcc94ada63aa2cb1d14dbfb49384b32ff9b4c0c5d17753bcd3df2e30d2e2d4a1481cff2a82d06b8826517afd06196412733a6e9f7fda")
      .content("=== Jerry test ===")
      .createdAt(1_684_578_866L)
      .kind(1)
      .pubkey("f2a18190fa309dde4dd8095f5f0fc1d19c431236e6815b51f19992283c816c88")
      .tags(List.of(List.of()))
      .build();

    client.send(event);
  }



}