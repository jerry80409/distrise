package com.github.jerry80409.nostr.ws;

import java.net.URI;
import lombok.extern.slf4j.Slf4j;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.framing.CloseFrame;
import org.java_websocket.handshake.ServerHandshake;

@Slf4j
public class WsClient extends WebSocketClient {

  public WsClient(URI serverUri) {
    super(serverUri);
  }

  /**
   * Called after an opening handshake has been performed and the given websocket is ready to be
   * written on.
   *
   * @param serverHandshake The handshake of the websocket instance
   */
  @Override
  public void onOpen(ServerHandshake serverHandshake) {
    log.info("Websocket client establish connection, http status msg: {}", serverHandshake.getHttpStatusMessage());
  }


  /**
   * Callback for string messages received from the remote host.
   *
   * @param callbackMsg The UTF-8 decoded message that was received.
   */
  @Override
  public void onMessage(String callbackMsg) {
    log.info("Websocket client received msg: {}", callbackMsg);
  }

  /**
   * Called after the websocket connection has been closed.
   *
   * @param code   The codes can be looked up here: {@link CloseFrame}
   * @param reason Additional information string
   * @param remote Returns whether or not the closing of the connection was initiated by the remote
   *               host.
   */
  @Override
  public void onClose(int code, String reason, boolean remote) {
    log.warn("Websocket connection is close({}), reason: {}, remote: {}", code, reason, remote);
  }

  /**
   * Called when errors occurs. If an error causes the websocket connection to fail {@link
   * #onClose(int, String, boolean)} will be called additionally.<br> This method will be called
   * primarily because of IO or protocol errors.<br> If the given exception is an RuntimeException
   * that probably means that you encountered a bug.<br>
   *
   * @param e The exception causing this error
   */
  @Override
  public void onError(Exception e) {
    log.error("Error occurred!", e);
  }

}