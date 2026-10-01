
package com.cyberai.controller;

import com.cyberai.model.*;
import com.cyberai.repository.*;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api")
public class ApiController {

 private final ThreatAlertRepository alerts;
 private final SecurityLogRepository logs;
 private final ThreatIntelRepository intel;

 public ApiController(
         ThreatAlertRepository alerts,
         SecurityLogRepository logs,
         ThreatIntelRepository intel) {

  this.alerts = alerts;
  this.logs = logs;
  this.intel = intel;
 }

 @GetMapping("/stats")
 Map<String, Object> stats() {
  return Map.of(
          "logs", logs.count(),
          "alerts", alerts.count(),
          "intel", intel.count()
  );
 }

 @GetMapping("/alerts")
 List<ThreatAlert> alerts() {
  return alerts.findTop25ByOrderByDetectedAtDesc();
 }
}