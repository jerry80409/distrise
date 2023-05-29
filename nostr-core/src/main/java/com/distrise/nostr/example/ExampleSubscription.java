package com.distrise.nostr.example;

import com.distrise.nostr.client.RelayClient;
import com.distrise.nostr.event.Subscription;

public class ExampleSubscription {

  private static final String URL = "wss://relay.nekolicio.us";

  private static final String SUBSCRIPTION_ID = "12j312n31knkajsndaksndas";

  public static void main(String[] args) {
    final RelayClient client = RelayClient.builder().url(URL).build();
    final Subscription subscription = new Subscription(SUBSCRIPTION_ID);

    client.subscribe(subscription);
  }
}