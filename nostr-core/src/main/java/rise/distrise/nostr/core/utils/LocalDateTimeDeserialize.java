package rise.distrise.nostr.core.utils;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class LocalDateTimeDeserialize extends StdDeserializer<LocalDateTime> {

  public LocalDateTimeDeserialize() {
    this(null);
  }

  protected LocalDateTimeDeserialize(Class<?> clazz) {
    super(clazz);
  }

  @Override
  public LocalDateTime deserialize(JsonParser parser, DeserializationContext context) throws IOException, JacksonException {
    return LocalDateTime.ofInstant(Instant.ofEpochSecond(Long.parseLong(parser.getText())), ZoneId.of("UTC").normalized());
  }
}