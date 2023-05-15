package rise.distrise.nostr.core.message.event;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.time.LocalDateTime;
import java.util.List;
import lombok.*;
import rise.distrise.nostr.core.Nkind;
import rise.distrise.nostr.core.utils.LocalDateTimeDeserialize;
import rise.distrise.nostr.core.utils.LocalDateTimeSerializer;
import rise.distrise.nostr.core.utils.NkindDeserialize;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(NON_NULL)
public class Nevent {

  /**
   * sha256 id
   */
  @EqualsAndHashCode.Include
  @JsonProperty("id")
  private String id;

  @JsonProperty("pubkey")
  private String pubkey;

  @JsonSerialize(using = LocalDateTimeSerializer.class)
  @JsonDeserialize(using = LocalDateTimeDeserialize.class)
  @JsonProperty("created_at")
  private LocalDateTime createdAt;

  @JsonDeserialize(using = NkindDeserialize.class)
  @JsonProperty("kind")
  private Nkind kind;

  @JsonProperty("tags")
  private List<List<String>> tags;

  @JsonProperty("content")
  private String content;

  @JsonProperty("sig")
  private String sig;

  @Builder
  public Nevent(String pubkey, LocalDateTime createdAt, Nkind kind, List<List<String>> tags, String content,
    String sig) {
    try {
      this.id = NeventId.builder().zero(0).pubkey(pubkey).createdAt(createdAt).kind(kind).tags(tags).content(content)
        .build().toSha256String();
    } catch (JsonProcessingException e) {
      throw new IllegalArgumentException(String
        .format("Error occurred when the id was sha256 serialized, the id: {}", id));
    }

    this.pubkey = pubkey;
    this.createdAt = createdAt;
    this.kind = kind;
    this.tags = tags;
    this.content = content;
    this.sig = sig;
  }

  /**
   * get id
   * @return NeventId
   */
  @JsonIgnore
  public NeventId getNeventId() {
    return NeventId.builder().zero(0).pubkey(pubkey).createdAt(createdAt).kind(kind).tags(tags).content(content)
      .build();
  }
}