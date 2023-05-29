package com.distrise.nostr.json;

import com.distrise.nostr.relay.message.RequestEvent;
import com.google.gson.*;
import java.lang.reflect.Type;

public class RequestEventAdaptor implements JsonSerializer<RequestEvent>, JsonDeserializer<RequestEvent> {

  @Override
  public RequestEvent deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
    throws JsonParseException {
    return new RequestEvent(json.getAsJsonArray().get(1).getAsString());
  }

  @Override
  public JsonElement serialize(RequestEvent src, Type typeOfSrc, JsonSerializationContext context) {
    JsonArray array = new JsonArray();
    array.add("REQ");
    array.add(src.subscriptionId());
    return array;
  }
}