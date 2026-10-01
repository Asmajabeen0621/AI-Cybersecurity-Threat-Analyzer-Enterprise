
package com.cyberai.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "blocked_ips")
public class BlockedIp {

 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long id;

 @Column(unique = true)
 private String ipAddress;

 private String reason;

 private LocalDateTime blockedAt;

 public Long getId() {
  return id;
 }

 public String getIpAddress() {
  return ipAddress;
 }

 public void setIpAddress(String v) {
  ipAddress = v;
 }

 public String getReason() {
  return reason;
 }

 public void setReason(String v) {
  reason = v;
 }

 public LocalDateTime getBlockedAt() {
  return blockedAt;
 }

 public void setBlockedAt(LocalDateTime v) {
  blockedAt = v;
 }
}