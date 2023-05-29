package com.distrise.nostr.nostrgateway.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;

@Slf4j
@RequiredArgsConstructor
public class RelayListener extends WebSocketListener {

  private final AmqpTemplate rabbitClient;

  private final DirectExchange exchange;

  private final Binding binding;

  @Override
  public void onClosed(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
    log.info("Websocket on closed [Relay: {}]", webSocket.request().url());

  }

  @Override
  public void onClosing(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
    log.info("Websocket on closing [Relay: {}]", webSocket.request().url());
  }

  @Override
  public void onFailure(@NotNull WebSocket webSocket, @NotNull Throwable t, @Nullable Response response) {
    log.error(String.format("Websocket on failure [Relay: %s]", webSocket.request().url()), t);
  }

  /**
   * Emit to Rabbit MQ
   *
   * @param webSocket
   * @param text
   */
  @Override
  public void onMessage(@NotNull WebSocket webSocket, @NotNull String text) {
    log.info("Websocket [Relay: {}] on text message: {}", webSocket.request().url(), text);
    rabbitClient.convertAndSend(exchange.getName(), binding.getRoutingKey(), text);
  }

  @Override
  public void onMessage(@NotNull WebSocket webSocket, @NotNull ByteString bytes) {
    log.info("Websocket [Relay: {}] on byte string: {}", webSocket.request().url(), bytes.toByteArray());
  }

  @Override
  public void onOpen(@NotNull WebSocket webSocket, @NotNull Response response) {
    log.info("Websocket [Relay: {}] on open, response: {}", webSocket.request().url(), response);
  }
}