package rise.distrise.nostr.core.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class NeventIdTest {

  private static final ObjectMapper MAPPER = new ObjectMapper();

  @Test
  void test_nevent_id_serialize_json() throws JsonProcessingException {
    final NeventId id = NeventId.builder().zero(0)
      .pubkey("56ed68e45f289b323407af4fb07a124ae7d5b32c3724233d5aa9d2647d4b22a8")
      .createdAt(LocalDateTime.of(2023, 5, 1, 7,11, 33))
      .kind(Nkind.SET_METADATA)
      .tags(List.of(List.of()))
      .content("hello").build();
    final String json = MAPPER.writeValueAsString(id);
    Assertions.assertEquals("[0,\"56ed68e45f289b323407af4fb07a124ae7d5b32c3724233d5aa9d2647d4b22a8\",1682925093,0,[[]],\"hello\"]", json);
  }

  @Test
  void test_nevent_id_sha256() throws JsonProcessingException {
    final NeventId id = NeventId.builder().zero(0)
      .pubkey("56ed68e45f289b323407af4fb07a124ae7d5b32c3724233d5aa9d2647d4b22a8")
      .createdAt(LocalDateTime.of(2023, 5, 1, 7,11, 33))
      .kind(Nkind.SET_METADATA)
      .tags(List.of(List.of()))
      .content("hello").build();

    final String sha256 = id.getSha256();
    Assertions.assertEquals("7cfce126a9d82a6dd9756e9b0bad8d142698ab8e21b20347813512d55b42c32a", sha256);
  }
}