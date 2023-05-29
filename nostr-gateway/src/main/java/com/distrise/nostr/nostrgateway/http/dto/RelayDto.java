package com.distrise.nostr.nostrgateway.http.dto;

import java.io.Serializable;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RelayDto implements Serializable {

  private String url;

  private String subscribedId;
}