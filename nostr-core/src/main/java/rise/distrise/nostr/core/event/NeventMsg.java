package rise.distrise.nostr.core.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Builder;

@Builder
public record NeventMsg(
  @JsonProperty("id") String id,
  @JsonProperty("pubkey") String pubkey,
  @JsonProperty("created_at") Long createdAt,
  @JsonProperty("kind") int kind,
  @JsonProperty("tags") List<List<String>> tags,
  @JsonProperty("content") String content,
  @JsonProperty("sig") String sig) implements NeventItem {


}