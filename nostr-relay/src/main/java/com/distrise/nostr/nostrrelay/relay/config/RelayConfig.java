package com.distrise.nostr.nostrrelay.relay.config;

import com.distrise.nostr.json.*;
import com.distrise.nostr.relay.message.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.RequiredArgsConstructor;
import okio.ByteString;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(RelayProperties.class)
public class RelayConfig {

  @Bean
  public Gson gson() {
    return new GsonBuilder()
      .registerTypeAdapter(ByteString.class, new HexByteStringAdaptor())
      .registerTypeAdapter(EndOfStoredEvent.class, new EndOfStoreEventAdaptor())
      .registerTypeAdapter(Notice.class, new NoticeMessageAdaptor())
      .registerTypeAdapter(EventMessage.class, new EventMessageAdaptor())
      .registerTypeAdapter(RequestEvent.class, new RequestEventAdaptor())
      .registerTypeAdapter(RelayMessage.class, new RelayMessageAdaptor())
      .create();
  }
}