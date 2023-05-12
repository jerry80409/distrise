package rise.distrise.nostr.core.request;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import rise.distrise.nostr.core.event.Nevent;
import rise.distrise.nostr.core.event.NeventType;
import rise.distrise.nostr.core.event.Nkind;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class EventRequestTest {

  private static final ObjectMapper MAPPER = new ObjectMapper();

  @Test
  void test_event_request_json_serialize() throws JsonProcessingException {
    final String pubkey = "56ed68e45f289b323407af4fb07a124ae7d5b32c3724233d5aa9d2647d4b22a8";
    final String content = "hello";
    final LocalDateTime timestamp = LocalDateTime.of(2023, 5, 1, 7,11, 33);
    final Nkind kind = Nkind.SET_METADATA;
    final List<List<String>> tags = List.of(List.of());

    final EventRequest request = new EventRequest();
    request.add(NeventType.EVENT);
    request.add(Nevent.builder().pubkey(pubkey).createdAt(timestamp).kind(kind).tags(tags)
      .content(content).build().updateId().getNevent01());

    final String json = MAPPER.writeValueAsString(request);
    Assertions.assertEquals("[\"EVENT\",{\"id\":\"7cfce126a9d82a6dd9756e9b0bad8d142698ab8e21b20347813512d55b42c32a\","
      + "\"pubkey\":\"56ed68e45f289b323407af4fb07a124ae7d5b32c3724233d5aa9d2647d4b22a8\",\"created_at\":1682925093,"
      + "\"kind\":0,\"tags\":[[]],\"content\":\"hello\",\"sig\":null}]", json);
  }
}