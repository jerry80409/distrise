package com.distrise.nostr.relay.message;

import com.distrise.nostr.event.EventType;
import java.io.Serializable;

public record RequestEvent(String subscriptionId) implements RelayMessage, Serializable {

  @Override
  public EventType getType() {
    return EventType.REQ;
  }
}