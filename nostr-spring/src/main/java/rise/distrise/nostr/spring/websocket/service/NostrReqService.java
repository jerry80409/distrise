package rise.distrise.nostr.spring.websocket.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.WebSocketSession;
import rise.distrise.nostr.core.message.req.ReqMessage;
import rise.distrise.nostr.spring.jpa.SessionRepository;
import rise.distrise.nostr.spring.jpa.SubscribeRepository;
import rise.distrise.nostr.spring.jpa.entity.SubscribeEntity;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class NostrReqService {

  private final SessionRepository sessionRepository;

  private final SubscribeRepository subscribeRepository;

  // todo - 定義 exception
  /**
   * 用 session 查相關 client 資料
   * 紀錄 session 要訂閱什麼
   */
  public void registerSub(WebSocketSession session, ReqMessage message) {
    final SubscribeEntity subscribeEntity = sessionRepository.findBySession(session.getId())
      .map(current -> SubscribeEntity.builder().pubkey(current.getId()).subId(message.getContent().subId()).build())
      .orElseThrow(() -> new IllegalStateException(String.format("Session (%s) not existed", session.getId())));
    subscribeRepository.save(subscribeEntity);
  }
}