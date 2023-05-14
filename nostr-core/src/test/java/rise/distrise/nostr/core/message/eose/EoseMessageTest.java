package rise.distrise.nostr.core.message.eose;

import static rise.distrise.nostr.core.utils.JsonUtils.JSON;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class EoseMessageTest {

  @Test
  void test_eose_message_json_serialize() throws JsonProcessingException {
    final EoseMessage eoseMsg = new EoseMessage("subId");
    final String json = JSON.writeValueAsString(eoseMsg);
    Assertions.assertThat(json).isEqualTo("[\"EOSE\",\"subId\"]");
  }
}