package com.distrise.nostr.nostrgateway.runner;

import com.distrise.nostr.nostrgateway.config.RabbitMqConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RelayListener extends WebSocketListener {

  private final RabbitTemplate rabbitClient;

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
    rabbitClient.convertAndSend(RabbitMqConfig.RELAY_EXCHANGE, RabbitMqConfig.RELAY_BINDING, text);
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