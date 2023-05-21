package com.distrise.nostr.client;

import com.distrise.nostr.crypto.PubKey;
import com.distrise.nostr.crypto.SecKey;
import okio.ByteString;
import org.junit.jupiter.api.Test;

// todo - mock web server https://www.cnblogs.com/plokmju/p/okhttp_weisocket.html
class RelayClientTest {

  private static final String URL = "wss://relay.nekolicio.us";

  private static final String PRIVATE_KEY = "64e38cc7fff2980d919f3c7ab6e5210e6575367dfc5b8a0a94c1ae7093cddcae";
  private static final String PUBLICK_KEY = "56ed68e45f289b323407af4fb07a124ae7d5b32c3724233d5aa9d2647d4b22a8";



  @Test
  void test() {
    final SecKey secKey = new SecKey(
      ByteString.decodeHex("64e38cc7fff2980d919f3c7ab6e5210e6575367dfc5b8a0a94c1ae7093cddcae"));
    final PubKey pubkey = secKey.pubkey();
    System.out.println("pubkey: " + pubkey.hex());
  }

  @Test
  void test2() {
    final SecKey secKey = new SecKey(ByteString.decodeHex(
      "ec9a89357ce7c35b4eef18175a5050d21ef703f738c3a0e3074f8136f72553be"));
    final PubKey pubKey = secKey.pubkey();
    System.out.println("pubkey: " + pubKey.hex());

  }


}