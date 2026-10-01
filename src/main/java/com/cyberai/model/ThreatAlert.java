package com.cyberai.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "threat_alerts")
public class ThreatAlert {

 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long id;

 private LocalDateTime detectedAt;
 private Long logId;
 private Long investigationId;

 private String username;
 private String ipAddress;

 @Enumerated(EnumType.STRING)
 private ThreatType threatType;

 @Enumerated(EnumType.STRING)
 private Severity severity;

 private int riskScore;
 private String mlPrediction;

 @Column(length = 4000)
 private String explanation;

 private boolean resolved;
 private boolean responseApplied;

 public Long getId() {
  return id;
 }

 public LocalDateTime getDetectedAt() {
  return detectedAt;
 }

 public void setDetectedAt(LocalDateTime detectedAt) {
  this.detectedAt = detectedAt;
 }

 public Long getLogId() {
  return logId;
 }

 public void setLogId(Long logId) {
  this.logId = logId;
 }

 public Long getInvestigationId() {
  return investigationId;
 }

 public void setInvestigationId(Long investigationId) {
  this.investigationId = investigationId;
 }

 public String getUsername() {
  return username;
 }

 public void setUsername(String username) {
  this.username = username;
 }

 public String getIpAddress() {
  return ipAddress;
 }

 public void setIpAddress(String ipAddress) {
  this.ipAddress = ipAddress;
 }

 public ThreatType getThreatType() {
  return threatType;
 }

 public void setThreatType(ThreatType threatType) {
  this.threatType = threatType;
 }

 public Severity getSeverity() {
  return severity;
 }

 public void setSeverity(Severity severity) {
  this.severity = severity;
 }

 public int getRiskScore() {
  return riskScore;
 }

 public void setRiskScore(int riskScore) {
  this.riskScore = riskScore;
 }

 public String getMlPrediction() {
  return mlPrediction;
 }

 public void setMlPrediction(String mlPrediction) {
  this.mlPrediction = mlPrediction;
 }

 public String getExplanation() {
  return explanation;
 }

 public void setExplanation(String explanation) {
  this.explanation = explanation;
 }

 public boolean isResolved() {
  return resolved;
 }

 public void setResolved(boolean resolved) {
  this.resolved = resolved;
 }

 public boolean isResponseApplied() {
  return responseApplied;
 }

 public void setResponseApplied(boolean responseApplied) {
  this.responseApplied = responseApplied;
 }
}