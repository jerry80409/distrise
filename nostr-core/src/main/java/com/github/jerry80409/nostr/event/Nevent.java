package com.github.jerry80409.nostr.event;

import static java.time.ZoneOffset.UTC;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializable;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import lombok.Builder;

@Builder
public record Nevent(
  @JsonProperty("id") NeventId id,
  @JsonProperty("pubkey") String pubkey,
  @JsonProperty("created_at") LocalDateTime createdAt,
  @JsonProperty("kind") Nkind kind,
  @JsonProperty("tags") List<List<String>> tags,
  @JsonProperty("content") String content,
  @JsonProperty("sig") String sig) implements NeventItem {

  /**
   * 把 ID 更新上去
   */
  @JsonIgnore
  public Nevent updateId() {
    if (Objects.isNull(id)) {
      return Nevent.builder().id(getEventId()).pubkey(pubkey).createdAt(createdAt).tags(tags).kind(kind)
        .content(content).sig(sig).build();
    }
    return this;
  }

  @JsonIgnore
  public NeventId getEventId() {
    return NeventId.builder().zero(0).pubkey(pubkey).createdAt(createdAt).tags(tags).kind(kind)
      .content(content).build();
  }

  @JsonIgnore
  public Nevent01 getNevent01() throws JsonProcessingException {
    if (Objects.isNull(id)) {
      throw new IllegalStateException("should updateId before");
    }
    return Nevent01.builder().id(id.getSha256()).pubkey(pubkey).createdAt(createdAt.toEpochSecond(UTC))
      .tags(tags).kind(kind.getValue()).content(content).sig(sig).build();
  }
}