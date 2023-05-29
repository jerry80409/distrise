package com.distrise.nostr.nostrgateway.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

// dummy relay setting
@Data
@ConfigurationProperties(prefix = "relay")
public class RelayProperties {

  private String url;

  private String subscribe;
}