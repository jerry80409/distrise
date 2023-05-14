package rise.distrise.nostr.spring.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

/**
 * WebSocketHandler 相當於 Mvc 的 Controller, 裡面定義了幾個可以實作的項目
 * <ul>
 * <li>當有 ws-client 連線時, {@link WebSocketHandler#afterConnectionClosed(WebSocketSession, CloseStatus)}</li>
 * <li>當有 ws-client 關閉連線時, {@link WebSocketHandler#afterConnectionClosed(WebSocketSession, CloseStatus)}</li>
 * <li>當有 ws-client 傳送資料過程中發生錯誤時, {@link WebSocketHandler#handleTransportError(WebSocketSession, Throwable)}</li>
 * <li>當有 ws-client 有接收到事件時, {@link WebSocketHandler#handleMessage(WebSocketSession, WebSocketMessage)}</li>
 * </ul>
 */
@Slf4j
class RelayHandler implements WebSocketHandler {

  @Override
  public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    log.info("An websocket client {} connected", session.getId());

  }

  @Override
  public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
    log.info("Received msg form {}: {}", session.getId(), message.getPayload());
  }

  @Override
  public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
    log.error(String.format("Error occurred with socket id: %s, the connection will be closed!", session.getId()),
      exception);
  }

  @Override
  public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
    log.warn("An websocket client {} closed by {}({})", session.getId(), closeStatus.getReason(), closeStatus.getCode());
  }

  @Override
  public boolean supportsPartialMessages() {
    return false;
  }
}