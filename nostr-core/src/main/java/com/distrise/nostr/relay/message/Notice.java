package com.distrise.nostr.relay.message;

import java.io.Serializable;

public record Notice(String message) implements RelayMessage, Serializable {

}