package com.distrise.nostr.json;

import com.google.gson.*;
import java.lang.reflect.Type;
import okio.ByteString;

public class HexByteStringAdaptor implements JsonSerializer<ByteString>, JsonDeserializer<ByteString> {

  @Override
  public ByteString deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
    throws JsonParseException {
    return ByteString.decodeHex(json.getAsString());
  }

  @Override
  public JsonElement serialize(ByteString src, Type typeOfSrc, JsonSerializationContext context) {
    return new JsonPrimitive(src.hex());
  }
}