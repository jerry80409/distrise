package com.distrise.nostr.crypto;

import com.distrise.nostr.crypto.bech32.Bech32;
import com.distrise.nostr.crypto.bech32.Bech32.Encoding;
import com.distrise.nostr.crypto.bech32.Bech32Prefix;
import fr.acinq.secp256k1.Secp256k1;
import java.util.Arrays;
import okio.ByteString;

public record SecKey(ByteString key) implements Key {

  public PubKey pubkey() {
    return new PubKey(ByteString.of(Arrays.copyOfRange(Secp256k1.Companion.pubKeyCompress(
      Secp256k1.Companion.pubkeyCreate(key.toByteArray())), 1, 33)));
  }

  public ByteString sign(ByteString payload) {
    return ByteString.of(Secp256k1.Companion.signSchnorr(payload.toByteArray(), key.toByteArray(), null));
  }

  @Override
  public String encoded() {
    return Bech32.encode(Encoding.BECH32, Bech32Prefix.NSEC.getCode(), key.toByteArray());
  }

  @Override
  public String hex() {
    return key.hex();
  }
}