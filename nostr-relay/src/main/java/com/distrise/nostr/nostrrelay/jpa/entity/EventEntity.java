package com.distrise.nostr.nostrrelay.jpa.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "EVENT", indexes = {
  @Index(name = "EVENT_SUB_PUBKEY_IDX", columnList = "SUBSCRIPTION,PUBKEY")})
public class EventEntity {

  @Id
  @ToString.Include
  @EqualsAndHashCode.Include
  @Column(name = "ID")
  private String id;

  @Column(name = "SUBSCRIPTION")
  private String subscription;

  @ToString.Include
  @Column(name = "CREATED_AT")
  private Long createdAt;

  @ToString.Include
  @Column(name = "PUBKEY")
  private String pubkey;

  @ToString.Include
  @Column(name = "CONTENT")
  private String content;

  @Column(name = "SIG")
  private String sig;
}