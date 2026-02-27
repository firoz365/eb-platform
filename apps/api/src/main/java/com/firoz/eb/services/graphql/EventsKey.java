package com.firoz.eb.services.graphql;


import com.firoz.eb.services.domain.EventType;

public record EventsKey(Long serviceId, EventType type) {}