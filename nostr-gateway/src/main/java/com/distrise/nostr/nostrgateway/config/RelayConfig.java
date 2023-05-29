package com.distrise.nostr.nostrgateway.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(RelayProperties.class)
public class RelayConfig {

  private final RelayProperties properties;

}