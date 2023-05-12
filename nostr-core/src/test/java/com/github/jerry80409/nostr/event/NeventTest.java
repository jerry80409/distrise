package com.github.jerry80409.nostr.event;

import static java.time.ZoneOffset.UTC;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.time.LocalDateTime;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class NeventTest {

  // 應該要先 updateId, 才能變成 nip-01 event
  @Test
  void test_event_update_id() {
    final String pubkey = "56ed68e45f289b323407af4fb07a124ae7d5b32c3724233d5aa9d2647d4b22a8";
    final String content = "hello";
    final LocalDateTime timestamp = LocalDateTime.of(2023, 5, 1, 7,11, 33);
    final Nkind kind = Nkind.SET_METADATA;
    final List<List<String>> tags = List.of(List.of());

    final Nevent event =
      Nevent.builder().pubkey(pubkey).createdAt(timestamp).kind(kind).content(content).tags(tags).build();
    final Nevent eventWithId = event.updateId();

    // has eventId
    Assertions.assertThat(eventWithId).isNotNull().isEqualTo(Nevent.builder()
      .id(NeventId.builder().zero(0).pubkey(pubkey).createdAt(timestamp).tags(tags).kind(kind).content(content).build())
      .pubkey(pubkey).createdAt(timestamp).kind(kind).tags(tags).content(content).build());
  }

  // 應該要先 updateId, 才能變成 nip-01 event
  @Test
  void test_event_get_nip_01_event() throws JsonProcessingException {
    final String pubkey = "56ed68e45f289b323407af4fb07a124ae7d5b32c3724233d5aa9d2647d4b22a8";
    final String content = "hello";
    final LocalDateTime timestamp = LocalDateTime.of(2023, 5, 1, 7,11, 33);
    final Nkind kind = Nkind.SET_METADATA;
    final List<List<String>> tags = List.of(List.of());

    // step1
    final Nevent event =
      Nevent.builder().pubkey(pubkey).createdAt(timestamp).kind(kind).content(content).tags(tags).build();
    // step2
    final Nevent eventWithId = event.updateId();
    // step3
    final Nevent01 nip01Event = eventWithId.getNevent01();

    Assertions.assertThat(nip01Event).isNotNull()
      .hasFieldOrPropertyWithValue("id", "7cfce126a9d82a6dd9756e9b0bad8d142698ab8e21b20347813512d55b42c32a")
      .hasFieldOrPropertyWithValue("pubkey", pubkey)
      .hasFieldOrPropertyWithValue("kind", kind.getValue())
      .hasFieldOrPropertyWithValue("content", content)
      .hasFieldOrPropertyWithValue("createdAt", timestamp.toEpochSecond(UTC))
      .extracting("tags").asList().hasSize(1);
  }
}