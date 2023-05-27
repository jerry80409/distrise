package com.distrise.nostr.json;

import com.distrise.nostr.relay.message.RelayMessage;
import com.google.gson.*;
import java.lang.reflect.Type;

public class RelayMessageAdaptor implements JsonSerializer<RelayMessage>, JsonDeserializer<RelayMessage> {

  @Override
  public RelayMessage deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
    throws JsonParseException {
    final JsonArray jsonArray = json.getAsJsonArray();
    final String type = jsonArray.get(0).getAsString();
    return switch (type) {
      case "EOSE" -> new EndOfStoreEventAdaptor().deserialize(json, typeOfT, context);
      case "EVENT" -> new EventMessageAdaptor().deserialize(json, typeOfT, context);
      default -> throw new UnsupportedOperationException("Unsupported type: " + type);
    };
  }

  @Override
  public JsonElement serialize(RelayMessage src, Type typeOfSrc, JsonSerializationContext context) {
    return context.serialize(src);
  }
}