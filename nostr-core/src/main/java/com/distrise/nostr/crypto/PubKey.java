package com.distrise.nostr.crypto;

import com.distrise.nostr.crypto.bech32.Bech32Prefix;
import okio.ByteString;

public record PubKey(ByteString key) implements Key {

  @Override
  public Bech32Prefix encoded() {
    return Bech32Prefix.NPUB;
  }

  @Override
  public String hex() {
    return key.hex();
  }
}