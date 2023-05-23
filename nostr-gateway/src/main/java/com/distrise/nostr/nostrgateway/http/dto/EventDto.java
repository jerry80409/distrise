package com.distrise.nostr.nostrgateway.http.dto;

import java.io.Serializable;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EventDto implements Serializable {

  private String id;

  private String subscription;

  private Long createdAt;

  private String pubkey;

  private String content;
}