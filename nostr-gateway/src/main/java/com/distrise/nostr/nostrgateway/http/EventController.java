package com.distrise.nostr.nostrgateway.http;

import static org.springframework.data.domain.Sort.Direction.DESC;

import com.distrise.nostr.nostrgateway.http.dto.EventQuery;
import com.distrise.nostr.nostrgateway.jpa.EventRepository;
import com.distrise.nostr.nostrgateway.jpa.entity.EventEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/event")
@RequiredArgsConstructor
class EventController {

  private final EventRepository eventRepository;

  @GetMapping
  @Transactional(readOnly = true)
  public Page<EventEntity> query(EventQuery conditions,
    @PageableDefault(size = 10, page = 0, sort = "id", direction = DESC) Pageable pageable) {
    return eventRepository.findBySubscriptionAndPubkey(conditions.subsciptionId(),
      conditions.pubkey(), pageable);
  }
}