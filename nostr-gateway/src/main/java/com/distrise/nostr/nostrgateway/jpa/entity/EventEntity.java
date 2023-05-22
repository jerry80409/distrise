package com.distrise.nostr.nostrgateway.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "EVENT")
public class EventEntity {

  @Id
  @ToString.Include
  @EqualsAndHashCode.Include
  @Column(name = "ID")
  private String id;

  @ToString.Include
  @Column(name = "CREATED_AT")
  private Long createdAt;

  @ToString.Include
  @Column(name = "PUBKEY")
  private String pubkey;

  @ToString.Include
  @Column(name = "CONTENT")
  private String content;

}