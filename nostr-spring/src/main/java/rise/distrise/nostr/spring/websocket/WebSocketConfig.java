package rise.distrise.nostr.spring.websocket;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistration;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * <code>@Configuration</code> - 用於宣告此 Class 為 spring boot 配置檔
 * <code>@EnableWebSocket</code> - 此配置檔會啟用 websocket (整個 spring boot 容器只要寫一個地方就好)
 * <code>@RequiredArgsConstructor</code> 搭配 <code>@Import(RelayHandler.class)</code> - 讓 {@link RelayHandler} 可以作為外部注入
 * <code>@EnableConfigurationProperties(NostrProperties.class)</code> - 讓 application-nost.yaml 的參數可作為 bean 注入 sprnig
 * boot
 */
@Slf4j
@Configuration
@EnableWebSocket
@RequiredArgsConstructor
@Import(RelayHandler.class)
@ConditionalOnProperty("nostr.enabled")
@EnableConfigurationProperties(NostrProperties.class)
public class WebSocketConfig implements WebSocketConfigurer {

  private final RelayHandler relayHandler;

  private final NostrProperties properties;

  private final Environment env;

  /**
   * 把 {@link RelayHandler} 註冊到 spring boot 的 websocket handler (理解為 mvc 的 controller)
   * 且從 {@link NostrProperties#getPath()} 取得設定的 websocket 路徑, 啟動後會自動監聽 ws://localhost:{port}/{path}
   * 設定 {@link WebSocketHandlerRegistration#setAllowedOrigins(String...)} 為 <code>*</code> 表示要遮蔽 CORS 檢查.
   */
  @Override
  public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
    log.info("WebSocket handler registry register: {}, allowed origins: {}",
      "ws://localhost:" + env.getProperty("server.port") + properties.getPath(), "*");
    registry.addHandler(relayHandler, properties.getPath())
      .setAllowedOrigins("*");
  }
}