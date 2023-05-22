package com.distrise.nostr.event;

import com.distrise.nostr.crypto.SecKey;
import com.distrise.nostr.json.HexByteStringAdaptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okio.ByteString;

public interface EventContent {

  Gson GSON = new GsonBuilder()
    .registerTypeAdapter(ByteString.class, new HexByteStringAdaptor())
    .create();

  Event sign(SecKey secKey);

  String contentToJson();
}