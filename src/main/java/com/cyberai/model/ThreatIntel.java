package com.cyberai.model;

import jakarta.persistence.*;

@Entity
@Table(name = "threat_intelligence")
public class ThreatIntel {

 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long id;

 @Column(unique = true, nullable = false)
 private String indicator;

 private String indicatorType;

 private String severity;

 private String source;

 @Column(length = 1000)
 private String description;

 private boolean active = true;

 // Getters and Setters

 public Long getId() {
  return id;
 }

 public String getIndicator() {
  return indicator;
 }

 public void setIndicator(String indicator) {
  this.indicator = indicator;
 }

 public String getIndicatorType() {
  return indicatorType;
 }

 public void setIndicatorType(String indicatorType) {
  this.indicatorType = indicatorType;
 }

 public String getSeverity() {
  return severity;
 }

 public void setSeverity(String severity) {
  this.severity = severity;
 }

 public String getSource() {
  return source;
 }

 public void setSource(String source) {
  this.source = source;
 }

 public String getDescription() {
  return description;
 }

 public void setDescription(String description) {
  this.description = description;
 }

 public boolean isActive() {
  return active;
 }

 public void setActive(boolean active) {
  this.active = active;
 }
}