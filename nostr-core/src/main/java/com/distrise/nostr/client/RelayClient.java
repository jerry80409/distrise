package com.distrise.nostr.client;

import com.distrise.nostr.crypto.SecKey;
import com.distrise.nostr.event.Event;
import com.distrise.nostr.event.Subscription;
import com.distrise.nostr.event.TextContent;
import com.distrise.nostr.json.HexByteStringAdaptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

@Slf4j
public class RelayClient {

  private static final int DEFAULT_THREAD_POOL_SIZE = 10;

  private final ExecutorService threadPool = Executors.newFixedThreadPool(DEFAULT_THREAD_POOL_SIZE);

  private final Gson gson = new GsonBuilder().registerTypeAdapter(ByteString.class, new HexByteStringAdaptor())
    .create();

  private final String url;

  private final WebSocket webSocket;

  private final WebSocketListener listener;


  @Builder(builderMethodName = "builder", builderClassName = "RelayClientBuilder")
  public RelayClient(String url, WebSocketListener callback) {
    this.url = url;

    // ping pong each per 20 seconds
    final OkHttpClient httpClient = new OkHttpClient().newBuilder().pingInterval(20, TimeUnit.SECONDS).build();

    // binding callback
    listener = Objects.nonNull(callback) ? callback : new RelayListener(url);
    this.webSocket = httpClient.newWebSocket(new Request.Builder().url(url).build(), listener);
  }

  /**
   * Send event
   */
  public void send(TextContent textContent, SecKey signKey) {
    send(List.of("EVENT", textContent.sign(signKey)));
  }

  /**
   * Send event
   */
  public void send(Event event) {
    send(List.of("EVENT", event));
  }

  /**
   * Send subscribed
   */
  public void subscribe(Subscription subscription) {
    // todo - filters
    send(List.of("REQ", subscription.id()));
  }

  /**
   * Send unsubscribed
   */
  public void unsubscribe(Subscription subscription) {
    send(List.of("CLOSE", subscription.id()));
  }

  /**
   * Close websocket
   */
  public void close() {
    webSocket.close(1000, "NORMAL CLOSURE");
  }

  private void send(List<?> message) {
    final String json = gson.toJson(message);
    log.info("Send to [{}] message: {}", url, json);
    threadPool.submit(() -> webSocket.send(json));
  }
}