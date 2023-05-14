package rise.distrise.nostr.core.message.event;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static rise.distrise.nostr.core.message.NmessageType.EVENT;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import java.io.IOException;
import java.util.List;
import lombok.Builder;
import rise.distrise.nostr.core.message.Nmessage;

@Builder
@JsonInclude(NON_NULL)
public class EventMessage extends Nmessage<Nevent> {

  private Nevent content;

  public EventMessage(Nevent content) {
    super(EVENT, content);
    this.content = content;
  }

  @Override
  public void serialize(JsonGenerator gen, SerializerProvider serializers) throws IOException {
    gen.writePOJO(List.of(EVENT, content));
  }

  @Override
  public void serializeWithType(JsonGenerator gen, SerializerProvider serializers, TypeSerializer typeSer)
    throws IOException {

  }
}