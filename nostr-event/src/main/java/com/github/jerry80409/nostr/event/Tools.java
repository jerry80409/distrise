package com.github.jerry80409.nostr.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigInteger;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import lombok.experimental.UtilityClass;
import org.bouncycastle.jce.ECNamedCurveTable;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.ECParameterSpec;
import org.bouncycastle.jce.spec.ECPrivateKeySpec;

@UtilityClass
public class Tools {

  public static final ObjectMapper JSON_MAPPER = new ObjectMapper();

  // consider - https://gist.github.com/nakov/b01f9434df3350bc9b1cbf9b04ddb605, look like easily
  // or this - https://metamug.com/article/security/sign-verify-digital-signature-ecdsa-java.html.
  // sign by private key
  public byte[] sign(String algorithm, byte[] data, PrivateKey privkey)
    throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
    final Signature signer = Signature.getInstance(algorithm, new BouncyCastleProvider());
    signer.initSign(privkey);
    signer.update(data);
    return signer.sign();
  }

  public byte[] sign(String algorithm, byte[] data, String hex64Privkey)
    throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, InvalidKeySpecException {
    final Signature signer = Signature.getInstance(algorithm, new BouncyCastleProvider());
    signer.initSign(privKeyFromHex64(hex64Privkey));
    signer.update(data);
    return signer.sign();
  }

  public PrivateKey privKeyFromHex64(String privkey) throws NoSuchAlgorithmException, InvalidKeySpecException {
    // it's try error with good luck XD.
    final ECParameterSpec spec = ECNamedCurveTable.getParameterSpec("secp256k1");
    final ECPrivateKeySpec privkeySpec = new ECPrivateKeySpec(new BigInteger(privkey, 16), spec);
    final KeyFactory factory = KeyFactory.getInstance("EC", new BouncyCastleProvider());
    return factory.generatePrivate(privkeySpec);
  }

  // verify sign by pubkey
  public boolean verifySign(String algorithm, byte[] data, PublicKey pubkey, byte[] sign)
    throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
    final Signature signer = Signature.getInstance(algorithm, new BouncyCastleProvider());
    signer.initVerify(pubkey);
    signer.update(data);
    return signer.verify(sign);
  }
}