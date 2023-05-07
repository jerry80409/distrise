package com.github.jerry80409.nostr.event;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.List;
import lombok.Builder;

/**
 *
 * @param id 32-bytes lowercase hex-encoded sha256 of the serialized event data.
 * @param pubkey 32-bytes lowercase hex-encoded public key of the event creator.
 * @param createdAt unix timestamp in seconds.
 * @param kind integer.
 * @param tags another event or recommended relay URL or else.
 * @param content arbitrary string
 * @param sig 64-bytes hex of the signature of the sha256 hash of the serialized event data, which is the same as the
 * "id" field.
 */
@Builder
@JsonInclude(Include.NON_NULL)
public record Event(
  @JsonProperty("id") String id,
  @JsonProperty("pubkey") String pubkey,
  @JsonProperty("created_at") Long createdAt,
  @JsonProperty("kind") int kind,
  @JsonProperty("tags") List<String> tags,
  @JsonProperty("content") String content,
  @JsonProperty("sig") String sig) implements IEvent {


  public String getHexSha256Id() throws JsonProcessingException {
    return EventId.builder()
      .id(0)
      .pubkey(pubkey)
      .createdAt(createdAt)
      .kind(kind)
      .tags(tags)
      .content(content)
      .build().hexSha256Serialize();
  }

  //  public Event signature(ECKeyPair keyPair) throws JsonProcessingException {
//    final String id = EventId.builder().id(0).pubkey(pubkey).createdAt(createdAt).kind(kind).tags(tags)
//      .content(content).build().hexSha256Serialize();
//
//    final String sig = Sign.signMessage(id.getBytes(UTF_8), keyPair).toString();
//
//    return null;
//  }
}