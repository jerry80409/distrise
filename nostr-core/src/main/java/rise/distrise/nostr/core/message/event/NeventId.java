package rise.distrise.nostr.core.message.event;

import static java.time.ZoneOffset.UTC;
import static rise.distrise.nostr.core.utils.JsonUtils.JSON;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializable;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.google.common.hash.Hashing;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import rise.distrise.nostr.core.Nkind;

@Builder
public record NeventId(
  int zero,
  String pubkey,
  LocalDateTime createdAt,
  Nkind kind,
  List<List<String>> tags,
  String content) implements JsonSerializable {

  @Override
  public void serialize(JsonGenerator gen, SerializerProvider serializers) throws IOException {
    gen.writePOJO(List.of(zero, pubkey, createdAt.toEpochSecond(UTC), kind, tags, content));
  }

  @Override
  public void serializeWithType(JsonGenerator gen, SerializerProvider serializers, TypeSerializer typeSer)
    throws IOException {
    // do nothing
  }

  /**
   * Sha256 hashing
   *
   * @return sha256 string
   * @throws JsonProcessingException json serialize error.
   */
  public String toSha256String() throws JsonProcessingException {
    return Hashing.sha256().hashBytes(JSON.writeValueAsBytes(this)).toString();
  }
}