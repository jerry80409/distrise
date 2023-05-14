package rise.distrise.nostr.core.message.notice;

import static org.junit.jupiter.api.Assertions.*;
import static rise.distrise.nostr.core.utils.JsonUtils.JSON;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class NoticeMessageTest {

  @Test
  void test_notice_message_json_serialize() throws JsonProcessingException {
    final NoticeMessage noticeMsg = new NoticeMessage("notice");
    final String json = JSON.writeValueAsString(noticeMsg);
    Assertions.assertThat(json).isEqualTo("[\"NOTICE\",\"notice\"]");
  }
}