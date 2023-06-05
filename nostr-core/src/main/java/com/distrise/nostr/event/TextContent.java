package com.distrise.nostr.event;

import static java.nio.charset.StandardCharsets.UTF_8;

import com.distrise.nostr.crypto.SecKey;
import java.io.Serializable;
import java.time.Instant;
import java.util.List;
import lombok.Builder;
import okio.ByteString;

/**
 * Client text event
 */
public class TextContent implements EventContent {

  private static final Integer KIND = 1;

  private final String content;

  private final List<List<String>> tags;

  @Builder
  public TextContent(String content, List<List<String>> tags) {
    this.content = content;
    this.tags = tags;
  }

  /**
   * follow <a href="https://github.com/nostr-protocol/nips/blob/master/01.md">NIP-01</a>
   * <p>
   * sign and transfer to Event
   */
  @Override
  public Event sign(SecKey secKey) {
    final long timestamp = Instant.now().getEpochSecond();
    final String contentJson = contentToJson();
    final String tagsJson = GSON.toJson(tags);
    final List<Serializable> eventId = List.of(
      0, secKey.pubkey().hex(), timestamp, KIND, tagsJson, contentJson);
    final ByteString id = ByteString.encodeString(GSON.toJson(eventId), UTF_8).sha256();
    final ByteString sig = secKey.sign(id);

    return Event.builder().id(id).createdAt(timestamp).kind(KIND).pubkey(secKey.pubkey().key())
      .content(content).tags(tags).sig(sig).build();
  }

  @Override
  public String contentToJson() {
    return GSON.toJson(content);
  }
}