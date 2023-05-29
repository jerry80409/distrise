package com.distrise.nostr.nostrgateway.http;

import static org.springframework.data.domain.Sort.Direction.DESC;

import com.distrise.nostr.nostrgateway.http.dto.EventDto;
import com.distrise.nostr.nostrgateway.http.dto.EventQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/event")
@RequiredArgsConstructor
class EventController {

  private final EventService service;

  /**
   * Query the Events from database.
   *
   * @param conditions selected by conditions
   * @param pageable pageable default size is 10.
   * @return Events data
   */
  @GetMapping
  public Page<EventDto> query(EventQuery conditions,
    @PageableDefault(size = 10, page = 0, sort = "createdAt", direction = DESC) Pageable pageable) {
    return service.query(pageable);
  }
}