package com.distrise.nostr.nostrgateway.http;

import com.distrise.nostr.nostrgateway.http.dto.EventDto;
import com.distrise.nostr.nostrgateway.jpa.EventRepository;
import com.distrise.nostr.nostrgateway.jpa.entity.EventEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
class EventService {

  private final EventRepository repository;

  @Transactional(readOnly = true)
  Page<EventDto> query(Pageable pageable) {
    return repository.findAll(pageable).map(this::mapper);
  }

  private EventDto mapper(EventEntity entity) {
    return EventDto.builder()
      .id(entity.getId())
      .content(entity.getContent())
      .subscription(entity.getSubscription())
      .createdAt(entity.getCreatedAt())
      .pubkey(entity.getPubkey())
      .build();
  }
}