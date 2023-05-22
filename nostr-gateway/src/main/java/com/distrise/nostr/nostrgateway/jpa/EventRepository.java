package com.distrise.nostr.nostrgateway.jpa;

import com.distrise.nostr.nostrgateway.jpa.entity.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<EventEntity, String>, JpaSpecificationExecutor<EventEntity> {

}