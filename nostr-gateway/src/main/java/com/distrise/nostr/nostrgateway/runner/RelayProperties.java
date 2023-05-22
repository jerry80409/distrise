package com.distrise.nostr.nostrgateway.runner;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

// dummy relay setting
@Data
@ConfigurationProperties(prefix = "relay")
public class RelayProperties {

  private String url;

  private String subscribe;
}