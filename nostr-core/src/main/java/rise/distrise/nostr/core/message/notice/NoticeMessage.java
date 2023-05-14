package rise.distrise.nostr.core.message.notice;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static rise.distrise.nostr.core.message.NmessageType.NOTICE;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import java.io.IOException;
import java.util.List;
import lombok.Builder;
import rise.distrise.nostr.core.message.Nmessage;

@Builder
@JsonInclude(NON_NULL)
public class NoticeMessage extends Nmessage<String> {

  private String notice;

  public NoticeMessage(String notice) {
    super(NOTICE, notice);
    this.notice = notice;
  }

  @Override
  public void serialize(JsonGenerator gen, SerializerProvider serializers) throws IOException {
    gen.writePOJO(List.of(NOTICE, notice));
  }

  @Override
  public void serializeWithType(JsonGenerator gen, SerializerProvider serializers, TypeSerializer typeSer)
    throws IOException {

  }
}