package rise.distrise.nostr.core.message.event;

import static rise.distrise.nostr.core.Nkind.SET_METADATA;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.time.LocalDateTime;
import java.util.List;
import jdk.jshell.Snippet.Kind;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import rise.distrise.nostr.core.Nkind;
import rise.distrise.nostr.core.utils.JsonUtils;

class NeventTest {

  @Test
  void test_nevent_json_serialize() throws JsonProcessingException {
    final String pubkey = "56ed68e45f289b323407af4fb07a124ae7d5b32c3724233d5aa9d2647d4b22a8";
    final String content = "hello";
    final LocalDateTime timestamp = LocalDateTime.of(2023, 5, 1, 7,11, 33);
    final Nkind kind = SET_METADATA;
    final List<List<String>> tags = List.of(List.of());
    final String sig =
      "9a36894cf53d36b3672739b18c6e674995fb07824f5ff5d6186d7cb0b65e528f3eaec398166adeab9a38d9424d45ea9e17e2054c87c645207f653974703a58bb";

    final Nevent event = Nevent.builder().pubkey(pubkey).createdAt(timestamp).kind(kind).tags(tags).content(content)
      .sig(sig).build();
    final String json = JsonUtils.JSON.writeValueAsString(event);

    Assertions.assertThat(json).isEqualTo("{\"id\":\"7cfce126a9d82a6dd9756e9b0bad8d142698ab8e21b20347813512d55b42c32a"
      + "\",\"pubkey\":\"56ed68e45f289b323407af4fb07a124ae7d5b32c3724233d5aa9d2647d4b22a8\",\"created_at\":1682925093,\"kind\":0,\"tags\":[[]],\"content\":\"hello\",\"sig\":\"9a36894cf53d36b3672739b18c6e674995fb07824f5ff5d6186d7cb0b65e528f3eaec398166adeab9a38d9424d45ea9e17e2054c87c645207f653974703a58bb\"}");
  }

  @Test
  void test_nevent_json_deserialize() throws JsonProcessingException {
    final String json = "{\"id\":\"7cfce126a9d82a6dd9756e9b0bad8d142698ab8e21b20347813512d55b42c32a"
      + "\",\"pubkey\":\"56ed68e45f289b323407af4fb07a124ae7d5b32c3724233d5aa9d2647d4b22a8\","
      + "\"created_at\":1682925093,\"kind\":0,\"tags\":[[]],\"content\":\"hello\",\"sig\":\"9a36894cf53d36b3672739b18c6e674995fb07824f5ff5d6186d7cb0b65e528f3eaec398166adeab9a38d9424d45ea9e17e2054c87c645207f653974703a58bb\"}";

    final Nevent nevent = JsonUtils.JSON.readValue(json, Nevent.class);
    Assertions.assertThat(nevent)
      .hasFieldOrPropertyWithValue("id", "7cfce126a9d82a6dd9756e9b0bad8d142698ab8e21b20347813512d55b42c32a")
      .hasFieldOrPropertyWithValue("pubkey", "56ed68e45f289b323407af4fb07a124ae7d5b32c3724233d5aa9d2647d4b22a8")
      .hasFieldOrPropertyWithValue("createdAt", LocalDateTime.of(2023, 5, 1, 7,11, 33))
      .hasFieldOrPropertyWithValue("kind", SET_METADATA)
      .hasFieldOrPropertyWithValue("content", "hello")
      .hasFieldOrPropertyWithValue("sig",
        "9a36894cf53d36b3672739b18c6e674995fb07824f5ff5d6186d7cb0b65e528f3eaec398166adeab9a38d9424d45ea9e17e2054c87c645207f653974703a58bb");

  }

}