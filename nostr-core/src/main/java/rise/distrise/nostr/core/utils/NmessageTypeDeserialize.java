package rise.distrise.nostr.core.utils;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import java.io.IOException;
import rise.distrise.nostr.core.message.NmessageType;

public class NmessageTypeDeserialize extends StdDeserializer<NmessageType> {

  public NmessageTypeDeserialize() {
    this(null);
  }

  protected NmessageTypeDeserialize(Class<?> clazz) {
    super(clazz);
  }

  @Override
  public NmessageType deserialize(JsonParser parser, DeserializationContext context) throws IOException, JacksonException {
    return NmessageType.valueOf(parser.getText());
  }
}