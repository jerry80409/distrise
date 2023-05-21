package com.distrise.nostr.nostrrelay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class NostrRelayApplication {

  public static void main(String[] args) {
    SpringApplication.run(NostrRelayApplication.class, args);
  }

}