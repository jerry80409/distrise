package com.distrise.nostr.client;

public interface ConnectionStateListener {

  void update(ConnectionState state);
}