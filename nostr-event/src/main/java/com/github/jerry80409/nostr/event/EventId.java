package com.github.jerry80409.nostr.event;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static java.nio.charset.StandardCharsets.UTF_8;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.hash.Hashing;
import java.math.BigInteger;
import java.util.List;
import lombok.Builder;

/**
 * because the id is integer not same as {@link Event} fields.
 */
@Builder
@JsonInclude(NON_NULL)
record EventId(
  @JsonProperty("id") int id,
  @JsonProperty("pubkey") String pubkey,
  @JsonProperty("created_at") Long createdAt,
  @JsonProperty("kind") int kind,
  @JsonProperty("tags") List<String> tags,
  @JsonProperty("content") String content) implements IEvent {

  // json
  public byte[] serialize() throws JsonProcessingException {
    return Tools.JSON_MAPPER.writeValueAsString(this)
//      .replace("\"", "'") // should I replace?, because java json always with double quote.
      .getBytes(UTF_8);
  }

  // sha256
  public byte[] sha256Serialize() throws JsonProcessingException {
    return Hashing.sha256().hashBytes(serialize()).asBytes();
  }

  // hex string
  public String hexSha256Serialize() throws JsonProcessingException {
    return new BigInteger(1, sha256Serialize()).toString(16);
  }
}