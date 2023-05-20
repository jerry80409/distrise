package com.distrise.nostr.client;

public enum ConnectionState {
  DISCONNECTED,
  CONNECTING,
  CONNECTED,
  FAILING,
  DISCONNECTING
}