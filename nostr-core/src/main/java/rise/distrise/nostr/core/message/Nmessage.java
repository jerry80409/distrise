package rise.distrise.nostr.core.message;

import com.fasterxml.jackson.databind.JsonSerializable;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import rise.distrise.nostr.core.utils.NmessageTypeDeserialize;

@Getter
@AllArgsConstructor
public abstract class Nmessage<T> implements JsonSerializable {

  @JsonDeserialize(using = NmessageTypeDeserialize.class)
  private NmessageType type;

  private T content;
}