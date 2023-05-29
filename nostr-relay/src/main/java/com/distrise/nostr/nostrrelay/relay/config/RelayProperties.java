package com.distrise.nostr.nostrrelay.relay.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "relay")
public class RelayProperties {

  private String path;

  private String subscriptionId;
}