package com.firoz.eb.services.persistence;


import com.firoz.eb.services.domain.EventEntity;
import com.firoz.eb.services.domain.EventType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface EventRepository extends JpaRepository<EventEntity, Long> {

    List<EventEntity> findByServiceIdIn(Collection<Long> serviceIds);
    List<EventEntity> findByServiceId(Long serviceId);
    List<EventEntity> findByServiceIdAndType(Long serviceId , EventType type);

    List<EventEntity> findByServiceIdInAndType(Collection<Long> serviceIds, EventType type);
}