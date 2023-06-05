package com.distrise.nostr.relay.message;

import java.io.Serializable;

public record RequestEvent(String subscriptionId) implements RelayMessage, Serializable {

}