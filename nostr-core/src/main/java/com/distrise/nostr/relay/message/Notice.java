package com.distrise.nostr.relay.message;

import com.distrise.nostr.event.EventType;
import java.io.Serializable;

public record Notice(String message) implements RelayMessage, Serializable {

  @Override
  public EventType getType() {
    // todo
    return null;
  }
}