package rise.distrise.nostr.core.message.event;

import static rise.distrise.nostr.core.utils.JsonUtils.JSON;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import rise.distrise.nostr.core.Nkind;

class NeventIdTest {

  @Test
  void test_nevent_id_json_serialize() throws JsonProcessingException {
    final NeventId id = NeventId.builder().zero(0)
      .pubkey("56ed68e45f289b323407af4fb07a124ae7d5b32c3724233d5aa9d2647d4b22a8")
      .createdAt(LocalDateTime.of(2023, 5, 1, 7,11, 33))
      .kind(Nkind.SET_METADATA)
      .tags(List.of(List.of()))
      .content("hello").build();
    final String json = JSON.writeValueAsString(id);

    Assertions.assertThat(json).isEqualTo("[0,\"56ed68e45f289b323407af4fb07a124ae7d5b32c3724233d5aa9d2647d4b22a8\","
      + "1682925093,0,[[]],\"hello\"]");
  }

  @Test
  void test_nevent_id_to_sha256_string() throws JsonProcessingException {
    final NeventId id = NeventId.builder()
      .zero(0)
      .pubkey("56ed68e45f289b323407af4fb07a124ae7d5b32c3724233d5aa9d2647d4b22a8")
      .createdAt(LocalDateTime.of(2023, 5, 1, 7, 11, 33))
      .kind(Nkind.SET_METADATA)
      .tags(List.of(List.of()))
      .content("hello")
      .build();

    final String sha256Str = id.toSha256String();
    Assertions.assertThat(sha256Str).isEqualTo("7cfce126a9d82a6dd9756e9b0bad8d142698ab8e21b20347813512d55b42c32a");
  }
}