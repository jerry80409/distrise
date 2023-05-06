package com.github.jerry80409.nostr.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.security.*;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Tools {

  public static final ObjectMapper JSON_MAPPER = new ObjectMapper();

  // sign by private key
  public byte[] sign(String algorithm, byte[] data, PrivateKey prvkey)
    throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
    final Signature signer = Signature.getInstance(algorithm);
    signer.initSign(prvkey);
    signer.update(data);
    return signer.sign();
  }

  // verify sign by pubkey
  public boolean verifySign(String algorithm, byte[] data, PublicKey pubkey, byte[] sign)
    throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
    final Signature signer = Signature.getInstance(algorithm);
    signer.initVerify(pubkey);
    signer.update(data);
    return signer.verify(sign);
  }


}