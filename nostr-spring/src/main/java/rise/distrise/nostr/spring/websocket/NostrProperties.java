package rise.distrise.nostr.spring.websocket;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "nostr")
class NostrProperties {

  boolean enabled;

  String path;
}