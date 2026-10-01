
package com.cyberai.service;

import com.cyberai.model.*;
import com.cyberai.repository.*;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.Locale;

@Service
public class ThreatAnalysisService {

 private final MlThreatClassifier ml;
 private final ThreatIntelRepository intel;

 private static final Set<String> PHISH = Set.of(
         "verify account",
         "click here",
         "urgent",
         "password",
         "otp",
         "confirm identity",
         "suspended"
 );

 private static final Set<String> MAL = Set.of(
         "powershell",
         "ransomware",
         "trojan",
         "keylogger",
         "payload",
         "reverse shell",
         "cmd.exe"
 );

 public ThreatAnalysisService(
         MlThreatClassifier ml,
         ThreatIntelRepository intel) {

  this.ml = ml;
  this.intel = intel;
 }

 public Result analyze(SecurityLog log) {

  // Convert the log message to lowercase
  String t = Optional.ofNullable(log.getMessage())
          .orElse("")
          .toLowerCase(Locale.ROOT);

  // Count phishing and malware indicators
  int p = (int) PHISH.stream()
          .filter(t::contains)
          .count();

  int m = (int) MAL.stream()
          .filter(t::contains)
          .count();

  // Calculate the initial risk score
  int score = Math.min(
          100,
          log.getFailedAttempts() * 10
                  + p * 15
                  + m * 25
  );

  // Check whether the log matches active threat intelligence
  double intelHit = intel.findAll()
          .stream()
          .anyMatch(x ->
                  x.isActive()
                          && t.contains(
                          x.getIndicator()
                                  .toLowerCase(Locale.ROOT)
                  )
          ) ? 1 : 0;

  // Increase the score if threat intelligence matches
  score = Math.min(
          100,
          score + (intelHit == 1 ? 25 : 0)
  );

  String prediction;

  // Use the ML classifier, with a rule-based fallback
  try {
   prediction = ml.predict(
           log.getFailedAttempts(),
           p,
           m,
           intelHit
   );

  } catch (Exception e) {

   prediction = score >= 70
           ? "HIGH"
           : score >= 35
           ? "MEDIUM"
           : "LOW";
  }

  // Convert the ML prediction into a severity enum
  Severity sev = switch (prediction.toUpperCase()) {

   case "CRITICAL" -> Severity.CRITICAL;
   case "HIGH" -> Severity.HIGH;
   case "MEDIUM" -> Severity.MEDIUM;

   default -> Severity.LOW;
  };

  // Determine the type of threat
  ThreatType type = m > 0
          ? ThreatType.MALWARE
          : p > 0
          ? ThreatType.PHISHING
          : log.getFailedAttempts() >= 3
          ? ThreatType.ANOMALY
          : ThreatType.SIGNATURE_MATCH;

  // Create an explanation for the analysis result
  String reason = "ML prediction=" + prediction
          + ". Features: failedAttempts="
          + log.getFailedAttempts()
          + ", phishingIndicators=" + p
          + ", malwareIndicators=" + m
          + ", threatIntelMatch=" + (intelHit == 1) + ".";

  return new Result(
          type,
          sev,
          score,
          prediction,
          reason
  );
 }

 // Result returned to the calling service
 public record Result(
         ThreatType type,
         Severity severity,
         int score,
         String prediction,
         String explanation) {
 }
}