package com.distrise.nostr.nostrrelay.relay.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Slf4j
@Configuration
@EnableWebSocket
@RequiredArgsConstructor
@Import({ RelayHandler.class, RelayInterceptor.class })
public class WebSocketConfig implements WebSocketConfigurer {

  private final RelayProperties properties;

  // handler the websocket event messages
  private final RelayHandler relayHandler;

  // verify something or logging
  private final RelayInterceptor relayInterceptor;

  @Override
  public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
    registry.addHandler(relayHandler, properties.getPath()).setAllowedOrigins("*")
      .addInterceptors(relayInterceptor);
  }

  /**
   * simple and easy websocket handler
   * ref: https://www.skypyb.com/2019/02/uncategorized/813/
   */
//  @Bean
//  public ServerEndpointExporter serverEndpointExporter() {
//    return new ServerEndpointExporter();
//  }
}