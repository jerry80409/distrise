package com.distrise.nostr.crypto.bech32;

import com.google.common.io.BaseEncoding;
import java.util.Locale;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CryptoUtils {

  public byte[] hexToBytes(String hex) {
    return BaseEncoding.base16().decode(hex.toUpperCase(Locale.ROOT));
  }

  public String bytesToHex(byte[] bytes) {
    return BaseEncoding.base16().encode(bytes).toLowerCase();
  }
}