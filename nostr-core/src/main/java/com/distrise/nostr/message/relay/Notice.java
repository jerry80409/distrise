package com.distrise.nostr.message.relay;

import java.io.Serializable;

public record Notice(String message) implements RelayMessage, Serializable {

}