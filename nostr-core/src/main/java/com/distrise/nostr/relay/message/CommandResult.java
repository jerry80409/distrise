package com.distrise.nostr.relay.message;

import java.io.Serializable;
import okio.ByteString;

public record CommandResult(ByteString eventId, Boolean success, String message) implements RelayMessage, Serializable {

}