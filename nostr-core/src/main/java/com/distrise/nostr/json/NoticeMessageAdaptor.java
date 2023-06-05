package com.distrise.nostr.json;

import com.distrise.nostr.relay.message.Notice;
import com.google.gson.*;
import java.lang.reflect.Type;

public class NoticeMessageAdaptor implements JsonSerializer<Notice>, JsonDeserializer<Notice> {

  @Override
  public Notice deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
    throws JsonParseException {
    return new Notice(json.getAsJsonArray().get(1).getAsString());
  }

  @Override
  public JsonElement serialize(Notice src, Type typeOfSrc, JsonSerializationContext context) {
    final JsonArray array = new JsonArray();
    array.add("NOTICE");
    array.add(src.message());
    return array;
  }
}