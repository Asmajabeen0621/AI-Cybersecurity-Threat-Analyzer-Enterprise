package com.cyberai.repository;

import com.cyberai.model.SecurityLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SecurityLogRepository
        extends JpaRepository<SecurityLog, Long> {

 List<SecurityLog> findTop25ByOrderByTimestampDesc();

 List<SecurityLog> findByIpAddressOrderByTimestampDesc(
         String ipAddress
 );

 long countByIpAddress(String ipAddress);

}