package com.distrise.nostr.relay.message;

import java.io.Serializable;

public record EndOfStoredEvent(String subscriptionId) implements RelayMessage, Serializable {

}