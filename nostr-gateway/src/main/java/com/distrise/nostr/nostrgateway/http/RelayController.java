package com.distrise.nostr.nostrgateway.http;

import com.distrise.nostr.client.RelayClient;
import com.distrise.nostr.client.Subscription;
import com.distrise.nostr.nostrgateway.http.dto.RelayDto;
import com.distrise.nostr.nostrgateway.runner.RelayListener;
import com.distrise.nostr.nostrgateway.runner.RelayProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * A simple API for subscribed the Relay
 */
@Slf4j
@RestController
@RequestMapping("/relay")
@RequiredArgsConstructor
public class RelayController {

  private final RelayListener relayListener;

  @PostMapping
  @ResponseStatus(code = HttpStatus.CREATED)
  void subscribed(@RequestBody RelayDto relayInfo) {
    final RelayClient dummyRelay = RelayClient.builder().url(relayInfo.getUrl()).listener(relayListener).build();
    dummyRelay.subscribe(new Subscription(relayInfo.getSubscribedId()));
    log.info("Subscribed Relay({}) / {}", relayInfo.getUrl(), relayInfo.getSubscribedId());
  }
}