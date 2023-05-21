package com.distrise.nostr.client;

import com.distrise.nostr.event.Event;
import com.distrise.nostr.event.json.HexByteStringAdaptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;
import okio.ByteString;

@Slf4j
public class RelayClient {

  private final WebSocket socket;

  // todo - 解耦它?
  private final Gson gson = new GsonBuilder()
    .registerTypeAdapter(ByteString.class, new HexByteStringAdaptor())
    .create();

  // todo - 由外面注入
  private final ExecutorService threads = Executors.newFixedThreadPool(10);

  @Builder
  public RelayClient(String url) {
    final OkHttpClient httpClient = new OkHttpClient().newBuilder().pingInterval(20, TimeUnit.SECONDS).build();
    socket = httpClient.newWebSocket(new Request.Builder().url(url).build(), new RelayListener(url));
  }

  /**
   * Send event
   */
  public void send(Event event) {
    send(List.of("EVENT", event));
  }

  // todo - filters
  public void subscribe(Subscription subscription) {
    send(List.of("REQ", subscription.id()));
  }

  public void unsubscribe(Subscription subscription) {
    send(List.of("CLOSE", subscription.id()));
  }

  private void send(List<?> message) {
    final String json = gson.toJson(message);
    log.info("Send the message: {}", json);
    threads.submit(() -> socket.send(json));
  }
}