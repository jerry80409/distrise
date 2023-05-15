package rise.distrise.nostr.spring.jpa.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * 紀錄 client 說了什麼
 */
@Setter
@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "EVENT", indexes = {
  @Index(name = "EVENT_PUBKEY_IDX", columnList = "PUBKEY")})
public class EventEntity {

  // NIP-01 裡面的 id
  @Id
  @Column(name = "ID")
  private String id;

  @Column(name = "CREATED_AT")
  private Long createdAt;

  @Column(name = "KIND")
  private Integer kind;

  @Column(name = "CONTENT")
  private String content;

  @Column(name = "PUBKEY")
  private String pubkey;

  @Column(name = "SIG")
  private String sig;

  // todo - tags
}