package com.distrise.nostr.client;

import static com.distrise.nostr.client.ConnectionState.CONNECTED;
import static com.distrise.nostr.client.ConnectionState.DISCONNECTED;
import static com.distrise.nostr.client.ConnectionState.FAILING;

import com.distrise.nostr.message.RelayMessage;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Slf4j
public class RelayListener extends WebSocketListener {

  private final String label;

//  private final ConnectionStateListener listener;

//  private final JsonAdapter<RelayMessage> relayMessageAdapter = new Moshi.Builder().build()
//    .adapter(RelayMessage.class);


  public RelayListener(String label) {
    this.label = label;
  }

//  public RelayListener(String label, ConnectionStateListener listener) {
//    this.label = label;
//    this.listener = listener;
//  }

  @Override
  public void onClosed(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
    log.info("WebSocket is closed. [Relay: {}]", this.label);
//    listener.update(DISCONNECTED);
  }

  @Override
  public void onClosing(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
    log.warn("WebSocket is closgin. [Relay: {}]", this.label);
//    listener.update(DISCONNECTED);
  }

  @Override
  public void onFailure(@NotNull WebSocket webSocket, @NotNull Throwable t, @Nullable Response response) {
    log.error("WebSocket failure. [Relay: {}]", this.label);
//    listener.update(FAILING);
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
//    this.listener.update(CONNECTED);
  }
}