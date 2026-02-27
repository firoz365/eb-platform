package com.firoz.eb.services.persistence;

import com.firoz.eb.services.domain.ServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


public interface ServiceRepository extends JpaRepository<ServiceEntity , Long>{
    Optional<ServiceEntity> findByNameIgnoreCase(String name);
    boolean existsByNameIgnoreCase(String name);


//    @EntityGraph(attributePaths = "events")
//    Optional<ServiceEntity> findWithEventsById(Long id); //loads service + events in one go.

}