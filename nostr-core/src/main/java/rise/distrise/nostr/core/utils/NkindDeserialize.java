package rise.distrise.nostr.core.utils;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import java.io.IOException;
import rise.distrise.nostr.core.Nkind;

public class NkindDeserialize extends StdDeserializer<Nkind> {

  private NkindDeserialize() {
    this(null);
  }

  protected NkindDeserialize(Class<?> clazz) {
    super(clazz);
  }

  @Override
  public Nkind deserialize(JsonParser parser, DeserializationContext context) throws IOException, JacksonException {
    return Nkind.valueOf(Integer.parseInt(parser.getText()));
  }
}