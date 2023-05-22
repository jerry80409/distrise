package com.distrise.nostr.event;

import com.distrise.nostr.json.HexByteStringAdaptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.List;
import okio.ByteString;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class EventTest {

  private final Gson gson = new GsonBuilder()
    .registerTypeAdapter(ByteString.class, new HexByteStringAdaptor())
    .create();

  @Test
  void event_json_serialize() {
    Event event = Event.builder()
      .id(ByteString.decodeHex("4fd9e844927aa5a5c01e857c32a8ee8598418aaab3025d437fce3a7d94e2ea3d"))
      .sig(ByteString.decodeHex("6dcaff62670486a39f5a739fe1112273aa03e0584056e6862390a966546613e40c11745759ef1d04c1750cb304c033209ee6ed77a272f3e0519f4abe505354e4"))
      .pubkey(ByteString.decodeHex("56ed68e45f289b323407af4fb07a124ae7d5b32c3724233d5aa9d2647d4b22a8"))
      .createdAt(16_846_406_39L)
      .kind(1).tags(List.of())
      .content("test")
      .build();

    final String json = gson.toJson(event);
    Assertions.assertThat(json).isEqualTo("{"
      + "\"id\":\"4fd9e844927aa5a5c01e857c32a8ee8598418aaab3025d437fce3a7d94e2ea3d\","
      + "\"pubkey\":\"56ed68e45f289b323407af4fb07a124ae7d5b32c3724233d5aa9d2647d4b22a8\","
      + "\"created_at\":1684640639,\"kind\":1,\"tags\":[],"
      + "\"content\":\"test\","
      + "\"sig\":\"6dcaff62670486a39f5a739fe1112273aa03e0584056e6862390a966546613e40c11745759ef1d04c1750cb304c033209ee6ed77a272f3e0519f4abe505354e4\"}");
  }

  @Test
  void event_json_deserialize() {
    final String json = "{"
      + "\"id\":\"4fd9e844927aa5a5c01e857c32a8ee8598418aaab3025d437fce3a7d94e2ea3d\","
      + "\"pubkey\":\"56ed68e45f289b323407af4fb07a124ae7d5b32c3724233d5aa9d2647d4b22a8\","
      + "\"created_at\":1684640639,"
      + "\"kind\":1,\"tags\":[],"
      + "\"content\":\"test\","
      + "\"sig\":\"6dcaff62670486a39f5a739fe1112273aa03e0584056e6862390a966546613e40c11745759ef1d04c1750cb304c033209ee6ed77a272f3e0519f4abe505354e4\"}";

    final Event event = gson.fromJson(json, Event.class);
    System.out.println(event);
  }

}