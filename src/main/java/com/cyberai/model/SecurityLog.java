package com.cyberai.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "security_logs")
public class SecurityLog {

 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long id;

 private LocalDateTime timestamp;
 private String username;
 private String ipAddress;

 @Column(length = 3000)
 private String message;

 private String eventType;
 private int failedAttempts;
 private String country;
 private boolean analyzed;

 // Investigation reference
 private Long investigationId;

 public Long getId() {
  return id;
 }

 public LocalDateTime getTimestamp() {
  return timestamp;
 }

 public void setTimestamp(LocalDateTime timestamp) {
  this.timestamp = timestamp;
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

 public String getMessage() {
  return message;
 }

 public void setMessage(String message) {
  this.message = message;
 }

 public String getEventType() {
  return eventType;
 }

 public void setEventType(String eventType) {
  this.eventType = eventType;
 }

 public int getFailedAttempts() {
  return failedAttempts;
 }

 public void setFailedAttempts(int failedAttempts) {
  this.failedAttempts = failedAttempts;
 }

 public String getCountry() {
  return country;
 }

 public void setCountry(String country) {
  this.country = country;
 }

 public boolean isAnalyzed() {
  return analyzed;
 }

 public void setAnalyzed(boolean analyzed) {
  this.analyzed = analyzed;
 }

 public Long getInvestigationId() {
  return investigationId;
 }

 public void setInvestigationId(Long investigationId) {
  this.investigationId = investigationId;
 }
}