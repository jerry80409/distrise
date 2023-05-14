package rise.distrise.nostr.core.message.req;

import static rise.distrise.nostr.core.message.NmessageType.REQ;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import java.io.IOException;
import java.util.List;
import lombok.Builder;
import rise.distrise.nostr.core.filter.NeventFilter;
import rise.distrise.nostr.core.message.Nmessage;

public class ReqMessage<I, F extends NeventFilter> extends Nmessage<ReqContent> {

  private I subId;

  private F filter;

  public ReqMessage(String subId, NeventFilter filter) {
    super(REQ, ReqContent.builder().subId(subId).filter(filter).build());
  }

  public ReqMessage(ReqContent content) {
    super(REQ, content);
  }

  @Override
  public void serialize(JsonGenerator gen, SerializerProvider serializers) throws IOException {
    gen.writePOJO(List.of(REQ, subId, filter));
  }

  @Override
  public void serializeWithType(JsonGenerator gen, SerializerProvider serializers, TypeSerializer typeSer)
    throws IOException {

  }
}