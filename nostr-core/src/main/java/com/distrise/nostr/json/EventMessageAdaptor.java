package com.distrise.nostr.json;

import com.distrise.nostr.event.Event;
import com.distrise.nostr.message.relay.EventMessage;
import com.google.gson.*;
import java.lang.reflect.Type;

public class EventMessageAdaptor implements JsonSerializer<EventMessage>, JsonDeserializer<EventMessage> {

  @Override
  public EventMessage deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
    throws JsonParseException {
    return EventMessage.builder().subscriptionId(json.getAsJsonArray().get(1).getAsString())
      .event(context.deserialize(json.getAsJsonArray().get(2), Event.class)).build();
  }

  @Override
  public JsonElement serialize(EventMessage src, Type typeOfSrc, JsonSerializationContext context) {
    JsonArray array = new JsonArray();
    array.add("EVENT");
    array.add(src.subscriptionId());
    array.add(context.serialize(src.event()));
    return array;
  }
}