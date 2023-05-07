package com.github.jerry80409.example;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.time.ZoneOffset.UTC;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jerry80409.nostr.event.Event;
import com.github.jerry80409.nostr.event.EventType;
import com.github.jerry80409.nostr.event.IEvent;
import com.github.jerry80409.nostr.event.Tools;
import com.github.jerry80409.nostr.ws.WsClient;
import com.google.common.base.Strings;
import java.math.BigInteger;
import java.net.URI;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import lombok.extern.slf4j.Slf4j;
import org.java_websocket.framing.CloseFrame;

/**
 * Hello world!
 */
@Slf4j
public class SentEventExample {

  private static final ObjectMapper JSON_MAPPER = new ObjectMapper();

  private static final String DUMMY_RELAY = "wss://relay.nekolicio.us/";

  public static void main(String[] args)
    throws InterruptedException, JsonProcessingException, NoSuchAlgorithmException, SignatureException, InvalidKeySpecException, InvalidKeyException {
    final String pubkey = System.getenv("pubkey");
    final String privkey = System.getenv("privkey");
    if (Strings.isNullOrEmpty(pubkey) || Strings.isNullOrEmpty(privkey)) {
      throw new IllegalArgumentException("Required sys env [pubkey] and [privkey]");
    }

    final WsClient wsClient = new WsClient(URI.create(DUMMY_RELAY));
    log.info("Client connecting...");
    wsClient.connectBlocking(3, TimeUnit.SECONDS);
    log.info("Client connecting success");

    final List<IEvent> list = new ArrayList<>();
    list.add(EventType.EVENT);

    final String msg = "Greeting from jerry";

    // fixme - not same with the Relay hexSha256Id fields
    // event.id
    final String id = Event.builder().pubkey(pubkey).createdAt(LocalDateTime.now().toEpochSecond(UTC)).kind(1)
      .tags(List.of()).content(msg).build().getHexSha256Id();

    // sig, jsut try error with good luck XD.
    final byte[] sig = Tools.sign("SHA256withECDSA", id.getBytes(UTF_8), privkey);
    final String hexSig = new BigInteger(1, sig).toString(16);

    list.add(
      Event.builder().id(id).pubkey(pubkey).createdAt(LocalDateTime.now().toEpochSecond(UTC)).kind(1).tags(List.of())
        .content(msg).sig(hexSig).build());

    final String payload = JSON_MAPPER.writeValueAsString(list);
    log.debug("nostr client send payload: {}", payload);

    final Runnable runnable = () -> {
      wsClient.send(payload);
      wsClient.close(CloseFrame.NORMAL);
    };
    runnable.run();
  }
}