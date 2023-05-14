package rise.distrise.nostr.core.utils;

import static java.time.ZoneOffset.UTC;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;
import java.time.LocalDateTime;

public class LocalDateTimeSerializer extends StdSerializer<LocalDateTime> {

  public LocalDateTimeSerializer() {
    this(null);
  }

  protected LocalDateTimeSerializer(Class<LocalDateTime> clazz) {
    super(clazz);
  }

  @Override
  public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider provider) throws IOException {
    gen.writeNumber(value.toEpochSecond(UTC));
  }
}