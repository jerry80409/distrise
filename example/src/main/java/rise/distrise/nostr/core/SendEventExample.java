package rise.distrise.nostr.core;

import static rise.distrise.nostr.core.utils.JsonUtils.JSON;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.base.Strings;
import java.math.BigInteger;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.java_websocket.framing.CloseFrame;
import rise.distrise.nostr.core.message.event.EventMessage;
import rise.distrise.nostr.core.message.event.Nevent;
import rise.distrise.nostr.core.utils.Signer;

// session 1 homework
@Slf4j
public class SendEventExample {

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
    final EventMessage message = new EventMessage(Nevent.builder()
      .pubkey(pubkey)
      .createdAt(LocalDateTime.now())
      .kind(Nkind.SET_METADATA)
      .tags(List.of(List.of()))
      .content(MSG)
      .sig(Signer.sign(MSG, pubkey))
      .build()
    );

    final String json = JSON.writeValueAsString(message);

    // websocket send event
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