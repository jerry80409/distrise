package com.distrise.nostr.json;

import com.distrise.nostr.relay.message.EndOfStoredEvent;
import com.google.gson.*;
import java.lang.reflect.Type;

public class EndOfStoreEventAdaptor implements JsonSerializer<EndOfStoredEvent>, JsonDeserializer<EndOfStoredEvent> {

  @Override
  public EndOfStoredEvent deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
    throws JsonParseException {
    return new EndOfStoredEvent(json.getAsJsonArray().get(1).getAsString());
  }

  @Override
  public JsonElement serialize(EndOfStoredEvent src, Type typeOfSrc, JsonSerializationContext context) {
    JsonArray array = new JsonArray();
    array.add("EOSE");
    array.add(src.subscriptionId());
    return array;
  }
}