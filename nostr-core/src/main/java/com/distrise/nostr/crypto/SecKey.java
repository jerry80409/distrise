package com.distrise.nostr.crypto;

import com.distrise.nostr.crypto.bech32.Bech32Prefix;
import fr.acinq.secp256k1.Secp256k1;
import java.util.Arrays;
import okio.ByteString;

/**
 * Secp256k1 private key. Supported Nostr <a href="https://github.com/nostr-protocol/nips/blob/master/01.md">NIP-01</a>,
 *
 * @param key <a href="https://square.github.io/okio/2.x/okio/okio/-byte-string/index.html">ByteString</a>
 */
public record SecKey(ByteString key) implements Key {

  /**
   * Use of the private key to generate the corresponding public key.
   * <p>
   * The public key is compressed, public key format is [x value(32bits), y value(32bits)], that will return the x
   * value.
   *
   * @return
   */
  public PubKey pubkey() {
    return new PubKey(ByteString.of(
      Arrays.copyOfRange(Secp256k1.Companion.pubKeyCompress(Secp256k1.Companion.pubkeyCreate(key.toByteArray())), 1,
        33)));
  }

  /**
   * Signature
   *
   * @param payload
   * @return
   */
  public ByteString sign(ByteString payload) {
    return ByteString.of(Secp256k1.Companion.signSchnorr(payload.toByteArray(), key.toByteArray(), null));
  }

  /**
   * Encode.
   *
   * @return
   */
  @Override
  public Bech32Prefix encoded() {
    return Bech32Prefix.NSEC;
  }

  /**
   * For human reading.
   *
   * @return
   */
  @Override
  public String hex() {
    return key.hex();
  }
}