package rise.distrise.nostr.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.java_websocket.framing.CloseFrame;
import rise.distrise.nostr.core.event.Nevent;
import rise.distrise.nostr.core.event.NeventType;
import rise.distrise.nostr.core.event.Nkind;
import rise.distrise.nostr.core.request.EventRequest;

// session 1 homework
@Slf4j
public class SendEventExample {

  private static final ObjectMapper JSON_MAPPER = new ObjectMapper();

  private static final String DUMMY_RELAY = "wss://relay.nekolicio.us/";

  private static final String LOCAL_HOST_RELAY = "ws://localhost:8080/distrise/jerry";

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
      .content(MSG).build().updateId().getNeventMsg());

    final String json = JSON_MAPPER.writeValueAsString(request);

    // websocket send event
    // websocket send event to Relay
    final WsClient wsClient = new WsClient(URI.create(LOCAL_HOST_RELAY));
    log.info("Client connecting...");
    wsClient.connectBlocking(3, TimeUnit.SECONDS);
    log.info("Client connecting success");

    final Runnable runnable = () -> {
      wsClient.send(json);
      wsClient.close(CloseFrame.NORMAL);
    };
    runnable.run();
  }
}