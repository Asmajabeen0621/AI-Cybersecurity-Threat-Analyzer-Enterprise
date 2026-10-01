package com.cyberai.service;

import com.cyberai.model.*;
import com.cyberai.repository.*;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Service
public class LogIngestionService {

 private final SecurityLogRepository logs;
 private final ThreatAlertRepository alerts;
 private final ThreatAnalysisService analysis;
 private final BlockedIpRepository blocked;
 private final InvestigationRepository investigations;

 public LogIngestionService(
         SecurityLogRepository logs,
         ThreatAlertRepository alerts,
         ThreatAnalysisService analysis,
         BlockedIpRepository blocked,
         InvestigationRepository investigations) {

  this.logs = logs;
  this.alerts = alerts;
  this.analysis = analysis;
  this.blocked = blocked;
  this.investigations = investigations;
 }

 public int importCsv(MultipartFile file) throws IOException {

  int count = 0;
  int alertCount = 0;
  int suspiciousCount = 0;

  // Create a separate investigation for every uploaded file
  Investigation investigation = new Investigation();

  investigation.setFileName(file.getOriginalFilename());
  investigation.setUploadedAt(LocalDateTime.now());
  investigation.setStatus("COMPLETED");
  investigation.setDescription(
          "AI-powered security investigation generated from uploaded CSV file."
  );

  investigation.setTotalLogs(0);
  investigation.setTotalAlerts(0);
  investigation.setSuspiciousCount(0);

  // Save investigation first so that its ID is generated
  investigations.save(investigation);

  // Get the generated investigation ID
  Long investigationId = investigation.getId();

  try (BufferedReader br = new BufferedReader(
          new InputStreamReader(
                  file.getInputStream(),
                  StandardCharsets.UTF_8))) {

   String line;
   boolean header = true;

   while ((line = br.readLine()) != null) {

    if (header) {
     header = false;
     continue;
    }

    if (line.isBlank()) {
     continue;
    }

    String[] c = line.split(",", -1);

    if (c.length < 6) {
     continue;
    }

    SecurityLog l = new SecurityLog();

    l.setTimestamp(LocalDateTime.now());
    l.setUsername(c[0].trim());
    l.setIpAddress(c[1].trim());
    l.setEventType(c[2].trim());
    l.setFailedAttempts(Integer.parseInt(c[3].trim()));
    l.setCountry(c[4].trim());
    l.setMessage(c[5].trim());

    // Link security log to its investigation
    l.setInvestigationId(investigationId);

    // Save security log
    logs.save(l);

    // Analyze the security log using AI rules and ML
    ThreatAnalysisService.Result r = analysis.analyze(l);

    l.setAnalyzed(true);
    logs.save(l);

    // Create threat alert
    ThreatAlert a = new ThreatAlert();

    a.setDetectedAt(LocalDateTime.now());
    a.setLogId(l.getId());
    a.setUsername(l.getUsername());
    a.setIpAddress(l.getIpAddress());
    a.setInvestigationId(investigationId);
    a.setThreatType(r.type());
    a.setSeverity(r.severity());
    a.setRiskScore(r.score());
    a.setMlPrediction(r.prediction());
    a.setExplanation(r.explanation());
    a.setResolved(false);

    boolean highRisk =
            r.severity() == Severity.HIGH ||
                    r.severity() == Severity.CRITICAL;

    a.setResponseApplied(highRisk);

    // Save threat alert
    alerts.save(a);

    alertCount++;

    if (highRisk) {
     suspiciousCount++;
    }

    // Add IP to blocked list if high-risk
    if (a.isResponseApplied()
            && !blocked.existsByIpAddress(a.getIpAddress())) {

     BlockedIp b = new BlockedIp();

     b.setIpAddress(a.getIpAddress());
     b.setReason(r.explanation());
     b.setBlockedAt(LocalDateTime.now());

     blocked.save(b);
    }

    count++;
   }
  }

  // Update investigation statistics
  investigation.setTotalLogs(count);
  investigation.setTotalAlerts(alertCount);
  investigation.setSuspiciousCount(suspiciousCount);

  investigations.save(investigation);

  return count;
 }
}