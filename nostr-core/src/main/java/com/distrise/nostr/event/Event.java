package com.distrise.nostr.event;

import com.google.gson.annotations.SerializedName;
import fr.acinq.secp256k1.Secp256k1;
import java.io.Serializable;
import java.util.List;
import lombok.Builder;
import okio.ByteString;

@Builder
public record Event(
  ByteString id,
  ByteString pubkey,
  @SerializedName("created_at") Long createdAt,
  Integer kind,
  List<List<String>> tags,
  String content,
  ByteString sig) implements Serializable {

  public boolean verifySign() {
    return Secp256k1.Companion.verifySchnorr(sig.toByteArray(), id.toByteArray(), pubkey.toByteArray());
  }
}