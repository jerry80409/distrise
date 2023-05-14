package rise.distrise.nostr.core.utils;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.SignatureException;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.experimental.UtilityClass;
import org.bouncycastle.asn1.sec.ECPrivateKey;
import org.bouncycastle.util.encoders.Hex;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Hash;
import org.web3j.crypto.Sign;
import org.web3j.crypto.Sign.SignatureData;

/**
 * 用來做簽名, 解簽名用的.
 * I don't understand the Crypto just follow the
 * https://gist.github.com/nakov/b01f9434df3350bc9b1cbf9b04ddb605
 * https://blog.csdn.net/weixin_43931372/article/details/123135911
 */
@UtilityClass
public class Signer {

  public String sign(String msg, String privHex) {
    final byte[] msgBytes = msg.getBytes(UTF_8);
    final ECKeyPair keyPair = ECKeyPair.create(new BigInteger(privHex, 16));
    final SignatureData signData = Sign.signMessage(msgBytes, keyPair, true);
    return Stream.of(signData.getV(), signData.getR(), signData.getS()).map(Hex::toHexString)
      .collect(Collectors.joining());
  }

  public boolean verify(String msg, String pubkey, String sign) throws SignatureException {
    final byte[] msgBytes = Hash.sha3(msg.getBytes(UTF_8));
    final byte[] v = Hex.decode(sign.substring(0, 2));
    final byte[] r = Hex.decode(sign.substring(2, 66));
    final byte[] s = Hex.decode(sign.substring(66, 130));

    final SignatureData signData = new SignatureData(v, r, s);
    final BigInteger verifyPubkey = Sign.signedMessageHashToKey(msgBytes, signData);

    final String verify = deriveCompressPubkey(verifyPubkey);

    return deriveCompressPubkey(verifyPubkey).equals(pubkey);
  }

  /**
   * 取得 pubkey
   */
  public BigInteger derivePubkey(String privHex) {
    return ECKeyPair.create(new BigInteger(privHex, 16)).getPublicKey();
  }

  /**
   * 取得 pubkey
   */
  public String deriveCompressPubkey(BigInteger pubkey) {
    return pubkey.toString(16).substring(0, 64);
  }
}