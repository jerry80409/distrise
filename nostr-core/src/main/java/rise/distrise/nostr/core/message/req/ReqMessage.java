package rise.distrise.nostr.core.message.req;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static rise.distrise.nostr.core.message.NmessageType.REQ;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import java.io.IOException;
import java.util.Objects;
import java.util.stream.Stream;
import lombok.Builder;
import rise.distrise.nostr.core.filter.NeventFilter;
import rise.distrise.nostr.core.message.Nmessage;

@JsonInclude(NON_NULL)
public class ReqMessage extends Nmessage<ReqContent> {

  private String subId;

  private NeventFilter filter;

  @Builder
  public ReqMessage(String subId) {
    super(REQ, ReqContent.builder().subId(subId).build());
    this.subId = subId;
  }

  @Builder
  public ReqMessage(String subId, NeventFilter filter) {
    super(REQ, ReqContent.builder().subId(subId).filter(filter).build());
    this.subId = subId;
    this.filter = filter;
  }

  @Builder
  public ReqMessage(ReqContent content) {
    super(REQ, content);
    this.subId = content.subId();
    this.filter = content.filter();
  }

  @Override
  public void serialize(JsonGenerator gen, SerializerProvider serializers) throws IOException {
    gen.writePOJO(Stream.of(REQ, subId, filter).filter(Objects::nonNull).toList());
  }

  @Override
  public void serializeWithType(JsonGenerator gen, SerializerProvider serializers, TypeSerializer typeSer)
    throws IOException {

  }
}