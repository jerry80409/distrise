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
import rise.distrise.nostr.core.message.event.EventMessage;
import rise.distrise.nostr.core.message.event.Nevent;
import rise.distrise.nostr.core.message.req.ReqMessage;
import rise.distrise.nostr.core.utils.Signer;

@Slf4j
public class SubEventExample {

  private static final String DUMMY_RELAY = "wss://relay.nekolicio.us/";

  private static final String LOCAL_HOST_RELAY = "ws://localhost:8080/distrise/jerry";

  private static final String SUB_RELAY_ID = "12j312n31knkajsndaksndas";

  public static void main(String[] args) throws JsonProcessingException, InterruptedException {
    final String pubkey = System.getenv("pubkey");
    final String privkey = System.getenv("privkey");
    if (Strings.isNullOrEmpty(pubkey) || Strings.isNullOrEmpty(privkey)) {
      throw new IllegalArgumentException("Required sys env [pubkey] and [privkey]");
    }

    final String connectMsg = "watch testing";
    final EventMessage connect = new EventMessage(Nevent.builder()
      .pubkey(pubkey)
      .createdAt(LocalDateTime.now())
      .kind(Nkind.SET_METADATA)
      .tags(List.of(List.of()))
      .content(connectMsg)
      .sig(Signer.sign(connectMsg, pubkey))
      .build()
    );
    final String connectJson = JSON.writeValueAsString(connect);

    // request
    final ReqMessage subscription = new ReqMessage(SUB_RELAY_ID);
    final String subJson = JSON.writeValueAsString(subscription);

    final WsClient wsClient = new WsClient(URI.create(LOCAL_HOST_RELAY));
    log.info("Client connecting...");
    wsClient.connectBlocking(3, TimeUnit.SECONDS);
    log.info("Client connecting success");

    final Runnable runnable = () -> {
      wsClient.send(connectJson);
      wsClient.send(subJson);
    };
    runnable.run();
  }
}