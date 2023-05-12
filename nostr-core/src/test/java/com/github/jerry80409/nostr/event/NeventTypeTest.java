package com.github.jerry80409.nostr.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class NeventTypeTest {

  private static final ObjectMapper MAPPER = new ObjectMapper();

  @Test
  void test_nevent_type_json_value() throws JsonProcessingException {
    final List<NeventItem> list = List.of(NeventType.EVENT);
    final String json = MAPPER.writeValueAsString(list);
    Assertions.assertEquals("[\"EVENT\"]", json);
  }
}