package rise.distrise.nostr.core.utils;

import java.security.SignatureException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class SignerTest {

  @Test
  void test_sign_data() throws SignatureException {
    final String msg = "hello";
    final String privHex = "ec9a89357ce7c35b4eef18175a5050d21ef703f738c3a0e3074f8136f72553be";
    final String sign = Signer.sign("hello", privHex);
    final String pubHex = "8dbb643cdff5734b5e2c02c00abad61969b296ec980f783fe0933468854c5d15";
    final boolean verify = Signer.verify(msg, pubHex, sign);
    Assertions.assertThat(verify).isTrue();
  }
}