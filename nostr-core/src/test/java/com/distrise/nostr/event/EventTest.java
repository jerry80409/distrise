package com.distrise.nostr.event;

import com.distrise.nostr.json.HexByteStringAdaptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.List;
import okio.ByteString;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class EventTest {

  private static final String FAKE_KEY = "56ed68e45f289b323407af4fb07a124ae7d5b32c3724233d5aa9d2647d4b22a8";

  private final Gson gson = new GsonBuilder()
    .registerTypeAdapter(ByteString.class, new HexByteStringAdaptor())
    .create();

  @Test
  void event_json_serialize() {
    Event event = Event.builder()
      .id(ByteString.decodeHex("fc0c9696794154f0384ccf3b9f2ab97b95a46c54e6727898d0ae96838078ee85"))
      .sig(ByteString.decodeHex("9a36894cf53d36b3672739b18c6e674995fb07824f5ff5d6186d7cb0b65e528f3eaec398166adeab9a38d9424d45ea9e17e2054c87c645207f653974703a58bb"))
      .pubkey(ByteString.decodeHex(FAKE_KEY))
      .createdAt(16_846_406_39L)
      .kind(1).tags(List.of())
      .content("test")
      .build();

    final String json = gson.toJson(event);
    Assertions.assertThat(json).isEqualTo("{"
      + "\"id\":\"fc0c9696794154f0384ccf3b9f2ab97b95a46c54e6727898d0ae96838078ee85\","
      + "\"pubkey\":\"56ed68e45f289b323407af4fb07a124ae7d5b32c3724233d5aa9d2647d4b22a8\","
      + "\"created_at\":1684640639,\"kind\":1,\"tags\":[],"
      + "\"content\":\"test\","
      + "\"sig\":\"9a36894cf53d36b3672739b18c6e674995fb07824f5ff5d6186d7cb0b65e528f3eaec398166adeab9a38d9424d45ea9e17e2054c87c645207f653974703a58bb\"}");
  }

  @Test
  void event_json_deserialize() {
    final String json = "{"
      + "\"id\":\"fc0c9696794154f0384ccf3b9f2ab97b95a46c54e6727898d0ae96838078ee85\","
      + "\"pubkey\":\"56ed68e45f289b323407af4fb07a124ae7d5b32c3724233d5aa9d2647d4b22a8\","
      + "\"created_at\":1684640639,"
      + "\"kind\":1,\"tags\":[],"
      + "\"content\":\"test\","
      + "\"sig\":\"9a36894cf53d36b3672739b18c6e674995fb07824f5ff5d6186d7cb0b65e528f3eaec398166adeab9a38d9424d45ea9e17e2054c87c645207f653974703a58bb\"}";

    final Event event = gson.fromJson(json, Event.class);
    Assertions.assertThat(event)
      .hasFieldOrPropertyWithValue("id", ByteString.decodeHex("fc0c9696794154f0384ccf3b9f2ab97b95a46c54e6727898d0ae96838078ee85"))
      .hasFieldOrPropertyWithValue("pubkey", ByteString.decodeHex("56ed68e45f289b323407af4fb07a124ae7d5b32c3724233d5aa9d2647d4b22a8"))
      .hasFieldOrPropertyWithValue("createdAt", 1_684_640_639L)
      .hasFieldOrPropertyWithValue("content", "test")
      .hasFieldOrPropertyWithValue("sig", ByteString.decodeHex("9a36894cf53d36b3672739b18c6e674995fb07824f5ff5d6186d7cb0b65e528f3eaec398166adeab9a38d9424d45ea9e17e2054c87c645207f653974703a58bb"))
      .hasFieldOrPropertyWithValue("kind", 1)
      .hasFieldOrPropertyWithValue("tags", List.of());
  }

  /**
   * Test case from
   * <a href="https://achq.notion.site/Distributed-Systems-Project-Briefing-00eaa7a219954bb1a346d73bf09164f2">Project Briefing</a>
   */
  @Test
  void test_event_verify_sign() {
    final Event event = Event.builder()
      .id(ByteString.decodeHex("fc0c9696794154f0384ccf3b9f2ab97b95a46c54e6727898d0ae96838078ee85"))
      .sig(ByteString.decodeHex("9a36894cf53d36b3672739b18c6e674995fb07824f5ff5d6186d7cb0b65e528f3eaec398166adeab9a38d9424d45ea9e17e2054c87c645207f653974703a58bb"))
      .pubkey(ByteString.decodeHex(FAKE_KEY))
      .createdAt(16_846_406_39L)
      .kind(1).tags(List.of())
      .content("test")
      .build();

    Assertions.assertThat(event).extracting(Event::verifySign).isEqualTo(true);
  }
}