package rise.distrise.nostr.core.message.close;

import static rise.distrise.nostr.core.message.NmessageType.CLOSE;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import java.io.IOException;
import java.util.List;
import lombok.Builder;
import rise.distrise.nostr.core.message.Nmessage;

@Builder
public class CloseMessage extends Nmessage<String> {

  private final String subId;

  public CloseMessage(String subId) {
    super(CLOSE, subId);
    this.subId = subId;
  }

  @Override
  public void serialize(JsonGenerator gen, SerializerProvider serializers) throws IOException {
    gen.writePOJO(List.of(CLOSE, subId));
  }

  @Override
  public void serializeWithType(JsonGenerator gen, SerializerProvider serializers, TypeSerializer typeSer)
    throws IOException {

  }
}