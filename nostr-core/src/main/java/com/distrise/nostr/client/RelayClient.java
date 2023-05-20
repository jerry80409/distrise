package com.distrise.nostr.client;

import com.distrise.nostr.event.Event;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;

@Slf4j
public class RelayClient {

  private final WebSocket socket;

  private final JsonAdapter<List> messageAdapter = new Moshi.Builder().build()
    .adapter(List.class);

  private final ExecutorService threads = Executors.newFixedThreadPool(10);


  @Builder
  public RelayClient(String url) {
    final OkHttpClient httpClient = new OkHttpClient().newBuilder()
      .pingInterval(20, TimeUnit.SECONDS).build();
    final RelayListener relayListener = new RelayListener(url);
    socket = httpClient.newWebSocket(new Request.Builder().url(url).build(), relayListener);
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

//  override fun unsubscribe(subscription: Subscription) {
//    subscriptions.remove(subscription)
//    send(listOf("CLOSE", subscription.id))
//  }

  private void send(List<?> message) {
    final String json = messageAdapter.toJson(message);
    log.info("Send the message: {}", json);
    threads.submit(() -> socket.send(messageAdapter.toJson(message)));
  }
}