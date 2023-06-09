package com.distrise.nostr.relay.message;

import com.distrise.nostr.event.Event;
import java.io.Serializable;
import lombok.Builder;

@Builder
public record EventMessage(String subscriptionId, Event event) implements RelayMessage, Serializable {

}