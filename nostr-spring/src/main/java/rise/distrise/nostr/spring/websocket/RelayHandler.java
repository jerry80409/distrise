package rise.distrise.nostr.spring.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import rise.distrise.nostr.core.message.eose.EoseMessage;
import rise.distrise.nostr.core.message.notice.NoticeMessage;
import rise.distrise.nostr.spring.websocket.service.DispatchService;

// todo - 功能不完整, 只做 NIP-01 sign 驗證

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
@Component
@RequiredArgsConstructor
class RelayHandler implements WebSocketHandler {

  private final DispatchService dispatchService;

  private final ObjectMapper objectMapper;

  @Override
  public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    log.info("An websocket client {} connected", session.getId());

  }

  /**
   * 這邊只處理 EVENT 與 REQ 事件,
   */
  @Override
  public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
    log.info("Received msg form {}: {}", session.getId(), message.getPayload());
    final TextMessage textMessage = (TextMessage) message;

    try {
      dispatchService.dispatch(session, textMessage);

    } catch (Throwable e) {
      log.error("Dispatch message error", e);
      session.sendMessage(new TextMessage(objectMapper.writeValueAsBytes(NoticeMessage.builder()
        .notice("dispatch message error").build())));
    }
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