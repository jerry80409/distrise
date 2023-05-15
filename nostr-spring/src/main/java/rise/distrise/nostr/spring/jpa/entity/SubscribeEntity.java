package rise.distrise.nostr.spring.jpa.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * 紀錄 client 要訂閱誰
 */
@Setter
@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "SUBSCRIBE")
public class SubscribeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;

  @Column(name = "PUBKEY")
  private String pubkey;

  @Column(name = "SUB_ID")
  private String subId;
}