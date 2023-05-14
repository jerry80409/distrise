package rise.distrise.nostr.core.message.close;

import static rise.distrise.nostr.core.utils.JsonUtils.JSON;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class CloseMessageTest {

  @Test
  void test_close_message_json_serialize() throws JsonProcessingException {
    final CloseMessage closeMsg = new CloseMessage("subId");
    final String json = JSON.writeValueAsString(closeMsg);
    Assertions.assertThat(json).isEqualTo("[\"CLOSE\",\"subId\"]");
  }
}