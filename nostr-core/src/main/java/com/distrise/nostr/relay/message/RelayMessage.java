package com.distrise.nostr.relay.message;

import com.distrise.nostr.event.EventType;

public interface RelayMessage {


  EventType getType();
}