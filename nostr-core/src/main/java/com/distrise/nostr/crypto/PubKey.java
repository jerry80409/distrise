package com.distrise.nostr.crypto;

import okio.ByteString;

public record PubKey(ByteString key) implements Key {

  @Override
  public String encoded() {
    return null;
  }

  @Override
  public String hex() {
    return key.hex();
  }
}