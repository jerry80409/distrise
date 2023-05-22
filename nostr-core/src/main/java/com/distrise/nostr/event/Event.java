package com.distrise.nostr.event;

import com.google.gson.annotations.SerializedName;
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


}