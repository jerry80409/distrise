package com.distrise.nostr.crypto;

import com.distrise.nostr.crypto.bech32.Bech32Prefix;

/**
 * Key interface
 */
public interface Key {

  /**
   * Bech32 encoded
   *
   * @return Bech32Prefix
   */
  Bech32Prefix encoded();

  /**
   * For human reading.
   *
   * @return
   */
  String hex();

}