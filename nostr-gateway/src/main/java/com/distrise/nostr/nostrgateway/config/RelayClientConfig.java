package com.distrise.nostr.nostrgateway.config;

import com.distrise.nostr.client.RelayClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RelayClientConfig {

  @Bean
  RelayClient relayClient() {
    return null;
  }
}