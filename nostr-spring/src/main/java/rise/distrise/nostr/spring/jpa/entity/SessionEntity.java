package rise.distrise.nostr.spring.jpa.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.Builder.Default;

/**
 * 紀錄 session
 */
@Setter
@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "SESSION")
public class SessionEntity {

  // 認 pubkey 作為 user id
  @Id
  @Column(name = "ID")
  private String id;

  @Column(name = "SESSION", nullable = false)
  private String session;

  // 用來做一些安全性狀態驗證的, 簽名失敗之類的吧？
  @Default
  @Column(name = "STATUS")
  private Integer status = 1;

}