package com.distrise.nostr.relay.message;

import com.distrise.nostr.event.EventType;
import java.io.Serializable;
import okio.ByteString;

public record CommandResult(ByteString eventId, Boolean success, String message) implements RelayMessage, Serializable {

  @Override
  public EventType getType() {
    // todo
    return null;
  }
}