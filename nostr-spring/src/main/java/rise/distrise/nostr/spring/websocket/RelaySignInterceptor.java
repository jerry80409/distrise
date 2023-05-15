package rise.distrise.nostr.spring.websocket;

import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

// todo - 思考研究如何用 interceptor 驗證 sign
/**
 * WebSocket Handler 攔截器
 */
@Slf4j
@Component
public class RelaySignInterceptor implements HandshakeInterceptor {

  @Override
  public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
    Map<String, Object> attributes) throws Exception {
    log.info("WebSocket connected from {}:{}", request.getRemoteAddress().getAddress(),
      request.getRemoteAddress().getPort());
    return true;
  }

  @Override
  public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
    Exception exception) {

  }
}