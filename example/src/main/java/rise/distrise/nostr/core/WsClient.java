package rise.distrise.nostr.core;

import java.net.URI;
import lombok.extern.slf4j.Slf4j;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

@Slf4j
class WsClient extends WebSocketClient {

  WsClient(URI serverUri) {
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