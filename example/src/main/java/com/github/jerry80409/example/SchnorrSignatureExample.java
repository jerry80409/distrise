package com.github.jerry80409.example;

import java.math.BigInteger;
import org.bouncycastle.util.encoders.Hex;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Hash;
import org.web3j.crypto.Sign;

/**
 * Asking form ChatGPT,
 */
public class SchnorrSignatureExample {

  private static final String _TEST = "ec9a89357ce7c35b4eef18175a5050d21ef703f738c3a0e3074f8136f72553be";
  private static final String PUBLIC_KEY = "8dbb643cdff5734b5e2c02c00abad61969b296ec980f783fe0933468854c5d15";

  public static String compressPubKey(BigInteger pubKey) {
    String pubKeyYPrefix = pubKey.testBit(0) ? "03" : "02";
    String pubKeyHex = pubKey.toString(16);
    String pubKeyX = pubKeyHex.substring(0, 64);
    return pubKeyYPrefix + pubKeyX;
  }

  public static void main(String[] args) throws Exception {
    //BigInteger privKey = Keys.createEcKeyPair().getPrivateKey();
    BigInteger privKey = new BigInteger(_TEST, 16);
    BigInteger pubKey = Sign.publicKeyFromPrivate(privKey);

    ECKeyPair keyPair = new ECKeyPair(privKey, pubKey);
    System.out.println("Private key: " + privKey.toString(16));
    System.out.println("Public key: " + pubKey.toString(16));
    System.out.println("Public key (compressed): " + compressPubKey(pubKey));

    String msg = "Message for signing";
    byte[] msgHash = Hash.sha3(msg.getBytes());
    Sign.SignatureData signature = Sign.signMessage(msgHash, keyPair, false);
    System.out.println("Msg: " + msg);
    System.out.println("Msg hash: " + Hex.toHexString(msgHash));
    System.out.printf("Signature: [v = %d, r = %s, s = %s]",
       new BigInteger(1, signature.getV()).intValue() - 27,
      Hex.toHexString(signature.getR()),
      Hex.toHexString(signature.getS()));

    System.out.println();

    BigInteger pubKeyRecovered = Sign.signedMessageToKey(msg.getBytes(), signature);
    System.out.println("Recovered public key: " + pubKeyRecovered.toString(16));

    boolean validSig = pubKey.equals(pubKeyRecovered);
    System.out.println("Signature valid? " + validSig);
  }

}