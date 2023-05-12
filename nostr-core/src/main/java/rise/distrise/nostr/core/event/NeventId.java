package rise.distrise.nostr.core.event;

import static java.time.ZoneOffset.UTC;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

@Builder
public record NeventId(
  int zero,
  String pubkey,
  LocalDateTime createdAt,
  Nkind kind,
  List<List<String>> tags,
  String content) implements NeventItem, JsonSerializable {

  @JsonIgnore
  public String getSha256() throws JsonProcessingException {
    return Hashing.sha256().hashBytes(MAPPER.writeValueAsBytes(this)).toString();
  }

  @Override
  public void serialize(JsonGenerator gen, SerializerProvider serializers) throws IOException {
    gen.writePOJO(List.of(zero, pubkey, createdAt.toEpochSecond(UTC), kind, tags, content));
  }

  @Override
  public void serializeWithType(JsonGenerator gen, SerializerProvider serializers, TypeSerializer typeSer)
    throws IOException {
    // do nothing
  }
}