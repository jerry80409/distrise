package com.distrise.nostr.client;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Slf4j
public class RelayListener extends WebSocketListener {

  /**
   * mark the Relay subscribe URL as label
   */
  private final String label;

  public RelayListener(String label) {
    this.label = label;
  }

  @Override
  public void onClosed(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
    log.info("WebSocket is closed. [Relay: {}]", this.label);

  }

  @Override
  public void onClosing(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
    log.warn("WebSocket is closing. [Relay: {}]", this.label);

  }

  @Override
  public void onFailure(@NotNull WebSocket webSocket, @NotNull Throwable t, @Nullable Response response) {
    log.error("WebSocket failure. [Relay: {}]", this.label);

  }

  @Override
  public void onMessage(@NotNull WebSocket webSocket, @NotNull String text) {
    log.info("Received text from [Relay: {}]: {}", label, text);

  }

  @Override
  public void onMessage(@NotNull WebSocket webSocket, @NotNull ByteString bytes) {
    log.info("Received text from [Relay: {}]: {}", label, bytes.toByteArray());
  }

  @Override
  public void onOpen(@NotNull WebSocket webSocket, @NotNull Response response) {
    log.info("WebSocket is open. [Relay: {}] [Response: {}]", this.label, response.message());
  }

}