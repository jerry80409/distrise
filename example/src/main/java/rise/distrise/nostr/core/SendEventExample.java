package rise.distrise.nostr.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import rise.distrise.nostr.core.event.Nevent;
import rise.distrise.nostr.core.event.NeventType;
import rise.distrise.nostr.core.event.Nkind;
import rise.distrise.nostr.core.request.EventRequest;
import com.google.common.base.Strings;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.framing.CloseFrame;
import org.java_websocket.handshake.ServerHandshake;

// session 1 homework
@Slf4j
public class SendEventExample {

  private static final ObjectMapper JSON_MAPPER = new ObjectMapper();

  private static final String DUMMY_RELAY = "wss://relay.nekolicio.us/";

  private static final String MSG = "Greeting form Jerry";

  public static void main(String[] args) throws JsonProcessingException, InterruptedException {
    final String pubkey = System.getenv("pubkey");
    final String privkey = System.getenv("privkey");
    if (Strings.isNullOrEmpty(pubkey) || Strings.isNullOrEmpty(privkey)) {
      throw new IllegalArgumentException("Required sys env [pubkey] and [privkey]");
    }

    // request
    final EventRequest request = new EventRequest();
    request.add(NeventType.EVENT);
    request.add(Nevent.builder()
        .pubkey(pubkey)
        .createdAt(LocalDateTime.now())
        .tags(List.of(List.of()))
        .kind(Nkind.SET_METADATA)
        .sig("jlkjlkjlkjlkjlkjalsdfasdfasdf")
      .content(MSG).build().updateId().getNevent01());

    final String json = JSON_MAPPER.writeValueAsString(request);

    // websocket send event
    // websocket send event to Relay
    final WsClient wsClient = new WsClient(URI.create(DUMMY_RELAY));
    log.info("Client connecting...");
    wsClient.connectBlocking(3, TimeUnit.SECONDS);
    log.info("Client connecting success");

    final Runnable runnable = () -> {
      wsClient.send(json);
      wsClient.close(CloseFrame.NORMAL);
    };
    runnable.run();
  }

  // just simple socket client
  @Slf4j
  static class WsClient extends WebSocketClient {

    public WsClient(URI serverUri) {
      super(serverUri);
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
      log.info("Websocket client establish connection, http status msg: {}", handshakedata.getHttpStatusMessage());
    }

    @Override
    public void onMessage(String message) {
      log.info("Websocket client received msg: {}", message);
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
      log.warn("Websocket connection is close({}), reason: {}, remote: {}", code, reason, remote);
    }

    @Override
    public void onError(Exception ex) {
      log.error("Error occurred!", ex);
    }
  }
}