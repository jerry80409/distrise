package com.distrise.nostr.crypto;

import static java.nio.charset.StandardCharsets.UTF_8;

import com.distrise.nostr.crypto.bech32.Bech32Prefix;
import com.google.common.primitives.Bytes;
import okio.ByteString;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class SecKeyTest {

  private static final String FAKE_KEY = "3757483dcedc76436505e6ef1ff62cf150bac47c02d901901e82f93424442e92";

  @Test
  void test_sec_key() {
    final SecKey key = new SecKey(ByteString.decodeHex(FAKE_KEY));
    Assertions.assertThat(key)
      .returns("3757483dcedc76436505e6ef1ff62cf150bac47c02d901901e82f93424442e92", SecKey::hex)
      .returns(Bech32Prefix.NSEC, SecKey::encoded);
  }

  @Test
  void test_sec_key_sign() {
    final SecKey key = new SecKey(ByteString.decodeHex(FAKE_KEY));
    final String payload = "hello";
    // message must be 32 bytes
    final ByteString signature = key.sign(ByteString.of(
      Bytes.ensureCapacity(payload.getBytes(UTF_8), 32, 0)));
    Assertions.assertThat(signature).extracting(ByteString::hex)
      .isEqualTo("1b112347eb5d7f281b644b10fa0f7b94b72f003a49ed127b12feb15b7ec4b118c327b5c98002bb9755c99a62871f8245ee20acf35018b19529cb17f895e0dfab");
  }
}