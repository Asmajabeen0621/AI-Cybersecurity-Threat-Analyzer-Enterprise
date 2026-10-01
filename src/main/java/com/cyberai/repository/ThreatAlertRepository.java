package com.cyberai.repository;

import com.cyberai.model.ThreatAlert;
import com.cyberai.model.Severity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ThreatAlertRepository
        extends JpaRepository<ThreatAlert, Long> {

 List<ThreatAlert> findTop25ByOrderByDetectedAtDesc();

 long countBySeverity(Severity s);

 List<ThreatAlert> findByIpAddressOrderByDetectedAtDesc(
         String ipAddress
 );

 long countByIpAddress(String ipAddress);

 long countByIpAddressAndResolvedFalse(String ipAddress);

}