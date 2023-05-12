package rise.distrise.nostr.core.event;

import com.fasterxml.jackson.annotation.JsonValue;

public enum NeventType implements NeventItem {

  @JsonValue
  EVENT,

  @JsonValue
  REQ,

  @JsonValue
  CLOSE
}