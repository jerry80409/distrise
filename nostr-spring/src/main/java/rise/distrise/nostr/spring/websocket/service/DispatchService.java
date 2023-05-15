package rise.distrise.nostr.spring.websocket.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import rise.distrise.nostr.core.Nkind;
import rise.distrise.nostr.core.message.NmessageType;
import rise.distrise.nostr.core.message.event.EventMessage;
import rise.distrise.nostr.core.message.event.Nevent;
import rise.distrise.nostr.core.message.notice.NoticeMessage;
import rise.distrise.nostr.core.message.req.ReqMessage;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class DispatchService {

  private final NostrEventService eventService;

  private final NostrReqService reqService;

  private final ObjectMapper objectMapper;

  public void dispatch(WebSocketSession session, TextMessage message) throws Throwable {
    // java is very hard to deserialize arbitrarily array object, very shit I know.
    final Object[] objects = objectMapper.readValue(message.asBytes(), Object[].class);
    final NmessageType type = NmessageType.valueOf((String) objects[0]);

    try {
      switch (type) {
        case EVENT -> {
          final Map<String, Object> event = (Map<String, Object>) objects[1];
          final long createdAt = Long.parseLong(event.get("created_at").toString());
          eventService.handler(session, EventMessage.builder().content(
            Nevent.builder().id((String) event.get("id")).pubkey((String) event.get("pubkey"))
              .kind(Nkind.valueOf((int) event.get("kind"))).tags((List<List<String>>) event.get("tags"))
              .content((String) event.get("content"))
              .createdAt(LocalDateTime.ofInstant(Instant.ofEpochSecond(createdAt), ZoneId.of("UTC").normalized()))
              .sig((String) event.get("sig")).build()).build());
        }
        case REQ -> {
          final String subscribeId = (String) objects[1];
          reqService.registerSub(session, ReqMessage.builder().subId(subscribeId).build());
        }
        default -> session.sendMessage(new TextMessage(objectMapper.writeValueAsBytes(
          NoticeMessage.builder().notice("unsupported the message " + type.name()).build())));
      }

    } catch (Exception e) {
      log.error("oops!!", e);
      session.sendMessage(new TextMessage(objectMapper.writeValueAsBytes(
        NoticeMessage.builder().notice("error occurred with " + e.getMessage()).build())));
    }
  }
}