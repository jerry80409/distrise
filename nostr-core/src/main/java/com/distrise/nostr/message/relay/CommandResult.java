package com.distrise.nostr.message.relay;

import java.io.Serializable;
import okio.ByteString;

public record CommandResult(ByteString eventId,
                            Boolean success,
                            String message) implements RelayMessage, Serializable {

}