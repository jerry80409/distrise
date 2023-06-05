package com.distrise.nostr.event;

import com.distrise.nostr.crypto.SecKey;
import java.util.List;
import okio.ByteString;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class TextContentTest {

  private static final String KEY = "64e38cc7fff2980d919f3c7ab6e5210e6575367dfc5b8a0a94c1ae7093cddcae";

  @Test
  void test_text_content_sign_as_event() {
    final TextContent textContent = TextContent.builder().content("hello").tags(List.of()).build();
    final SecKey key = new SecKey(ByteString.decodeHex(KEY));
    final Event event = textContent.sign(key);
    Assertions.assertThat(event)
      .hasFieldOrPropertyWithValue("pubkey", ByteString.decodeHex("56ed68e45f289b323407af4fb07a124ae7d5b32c3724233d5aa9d2647d4b22a8"))
      .hasFieldOrPropertyWithValue("content", "hello")
      .hasFieldOrPropertyWithValue("kind", 1)
      .hasFieldOrPropertyWithValue("tags", List.of());
  }
}