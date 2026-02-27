package com.firoz.eb.services.graphql;

import com.firoz.eb.services.domain.EventEntity;
import com.firoz.eb.services.domain.EventType;
import com.firoz.eb.services.persistence.EventRepository;
import org.dataloader.DataLoader;
import org.dataloader.DataLoaderFactory;
import org.dataloader.DataLoaderRegistry;
import org.dataloader.MappedBatchLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Configuration
public class DataLoaderConfig {

    @Bean
    public DataLoaderRegistry dataLoaderRegistry(EventRepository eventRepository) {
        DataLoaderRegistry registry = new DataLoaderRegistry();

        MappedBatchLoader<EventsKey, List<EventEntity>> mappedBatchLoader = keys -> CompletableFuture.supplyAsync(() -> {

            // Group keys by serviceId (most efficient way in most cases)
            Map<Long, Set<EventType>> grouped = keys.stream()
                    .collect(Collectors.groupingBy(
                            EventsKey::serviceId,
                            Collectors.mapping(EventsKey::type, Collectors.toSet())
                    ));

            // In real apps → one query per service with IN(type) or separate per (service,type) pair
            // Here: simple but correct implementation
            return keys.stream()
                    .collect(Collectors.toMap(
                            key -> key,
                            key -> {
                                Long serviceId = key.serviceId();
                                EventType type = key.type();  // null = all

                                if (type == null) {
                                    return eventRepository.findByServiceId(serviceId);
                                } else {
                                    return eventRepository.findByServiceIdAndType(serviceId, type);
                                }
                            }
                    ));
        });

        DataLoader<EventsKey, List<EventEntity>> loader =
                DataLoaderFactory.newMappedDataLoader(mappedBatchLoader);

        registry.register("eventsByServiceAndType", loader);
        return registry;
    }
}