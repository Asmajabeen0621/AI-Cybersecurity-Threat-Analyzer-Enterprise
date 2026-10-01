package com.cyberai.repository;

import com.cyberai.model.BlockedIp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BlockedIpRepository extends JpaRepository<BlockedIp, Long> {

    boolean existsByIpAddress(String ipAddress);

    Optional<BlockedIp> findByIpAddress(String ipAddress);

}