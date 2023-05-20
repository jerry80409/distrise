package com.distrise.nostr.event;

import java.util.List;
import lombok.Builder;

@Builder
public record Event(
  String id,
  String pubkey,
  Long createdAt,
  Integer kind,
  List<List<String>> tags,
  String content,
  String sig) {

}