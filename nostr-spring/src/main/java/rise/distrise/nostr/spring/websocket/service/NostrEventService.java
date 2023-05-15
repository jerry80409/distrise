package rise.distrise.nostr.spring.websocket.service;

import static java.time.ZoneOffset.UTC;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.security.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.WebSocketSession;
import rise.distrise.nostr.core.message.eose.EoseMessage;
import rise.distrise.nostr.core.message.event.EventMessage;
import rise.distrise.nostr.spring.jpa.EventRepository;
import rise.distrise.nostr.spring.jpa.SessionRepository;
import rise.distrise.nostr.spring.jpa.entity.EventEntity;
import rise.distrise.nostr.spring.jpa.entity.SessionEntity;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class NostrEventService {

  private final SignService signService;

  private final SessionRepository sessionRepository;

  private final EventRepository eventRepository;

  /**
   * 簡單紀錄 event.
   * 回傳 eose message.
   */
  public EoseMessage handler(WebSocketSession session, EventMessage message)
    throws JsonProcessingException, SignatureException {
    // I can not verify the sign
    // verify sign
//    if (!signService.verify(message)) {
//      log.error("session {} - signature verify failed, pubkey: {}", session.getId(), message.getContent().getPubkey());
//      throw new SignatureException("signature verify failed");
//    }

    // 用 pubkey 辨識 client, 存入 session
    sessionRepository.save(SessionEntity.builder()
      .id(message.getContent().getPubkey())
      .session(session.getId())
      .status(1).build());

    // 紀錄 client 說了什麼
    final String msgId = message.getContent().getNeventId().toSha256String();
    eventRepository.save(mapper(msgId, message));

    // 回傳 EOSE
    return EoseMessage.builder().subId("sample-sub-id").build();
  }

  private EventEntity mapper(String id, EventMessage message) {
    return EventEntity.builder()
      .id(id)
      .createdAt(message.getContent().getCreatedAt().toEpochSecond(UTC))
      .kind(message.getContent().getKind().getValue())
      .content(message.getContent().getContent())
      .pubkey(message.getContent().getPubkey())
      .sig(message.getContent().getSig())
      .build();
  }
}