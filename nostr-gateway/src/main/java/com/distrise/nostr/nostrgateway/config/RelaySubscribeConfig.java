package com.distrise.nostr.nostrgateway.config;

import com.distrise.nostr.client.RelayClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnBean(RelayClient.class)
class RelaySubscribeConfig {

}