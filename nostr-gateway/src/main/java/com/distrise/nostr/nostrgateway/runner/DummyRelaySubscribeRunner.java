package com.distrise.nostr.nostrgateway.runner;

import com.distrise.nostr.client.RelayClient;
import com.distrise.nostr.client.Subscription;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@EnableConfigurationProperties(RelayProperties.class)
class DummyRelaySubscribeRunner implements ApplicationRunner {

  /**
   * dummy Relay properties (url, subscribeId)
   */
  private final RelayProperties properties;

  /**
   * handle the Relay websocket message callback and other things.
   */
  private final RelayListener relayListener;

  /**
   * Running after spring boot started
   *
   * @param args incoming application arguments
   * @throws Exception
   */
  @Override
  public void run(ApplicationArguments args) throws Exception {
    final RelayClient dummyRelay = RelayClient.builder().url(properties.getUrl()).listener(relayListener).build();
    dummyRelay.subscribe(new Subscription(properties.getSubscribe()));
    log.info("Subscribed DummyRelay({}) / {}", properties.getUrl(), properties.getSubscribe());
  }
}