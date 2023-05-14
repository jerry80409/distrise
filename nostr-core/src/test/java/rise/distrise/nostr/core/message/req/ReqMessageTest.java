package rise.distrise.nostr.core.message.req;

import static rise.distrise.nostr.core.utils.JsonUtils.JSON;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class ReqMessageTest {

  @Test
  void test_req_message_json_serialize() throws JsonProcessingException {
    final ReqMessage reqMessage = new ReqMessage(ReqContent.builder().subId("12j312n31knkajsndaksndas").build());
    final String json = JSON.writeValueAsString(reqMessage);
    Assertions.assertThat(json).isEqualTo("[\"REQ\",\"12j312n31knkajsndaksndas\"]");
  }
}