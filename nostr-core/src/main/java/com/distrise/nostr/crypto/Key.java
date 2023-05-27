package com.distrise.nostr.crypto;

/**
 * Key interface
 */
public interface Key {

  /**
   * encoded
   *
   * @return
   */
  String encoded();

  /**
   * For human reading.
   *
   * @return
   */
  String hex();

}