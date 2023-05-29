package com.distrise.nostr.nostrgateway.config;

import com.distrise.nostr.nostrgateway.runner.RelayProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(RelayProperties.class)
public class RelayConfig {

  private final RelayProperties properties;

}