package com.cyberai.repository;

import com.cyberai.model.Investigation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvestigationRepository
        extends JpaRepository<Investigation, Long> {

    List<Investigation> findAllByOrderByUploadedAtDesc();

}