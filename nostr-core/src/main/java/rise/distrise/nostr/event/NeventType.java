package rise.distrise.nostr.event;

import com.fasterxml.jackson.annotation.JsonValue;

public enum NeventType implements NeventItem {

  @JsonValue
  EVENT,

  @JsonValue
  REQ,

  @JsonValue
  CLOSE
}