package com.distrise.nostr.nostrgateway.http.dto;

import java.io.Serializable;
import lombok.Builder;

@Builder
public record EventQuery(
  String subsciptionId,
  String pubkey) implements Serializable {

}