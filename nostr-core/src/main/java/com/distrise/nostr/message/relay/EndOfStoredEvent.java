package com.distrise.nostr.message.relay;

import java.io.Serializable;

public record EndOfStoredEvent(String subscriptionId) implements RelayMessage, Serializable {

}