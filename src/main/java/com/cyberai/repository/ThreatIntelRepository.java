package com.cyberai.repository;

import com.cyberai.model.ThreatIntel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ThreatIntelRepository
        extends JpaRepository<ThreatIntel, Long> {

 Optional<ThreatIntel> findByIndicatorAndActiveTrue(
         String indicator
 );

}