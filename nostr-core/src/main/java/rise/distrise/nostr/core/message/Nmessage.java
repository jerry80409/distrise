package rise.distrise.nostr.core.message;

import com.fasterxml.jackson.databind.JsonSerializable;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public abstract class Nmessage<T> implements JsonSerializable {

  private NmessageType type;

  private T content;
}