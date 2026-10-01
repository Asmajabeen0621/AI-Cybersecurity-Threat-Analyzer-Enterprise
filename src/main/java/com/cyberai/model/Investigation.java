package com.cyberai.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "investigations")
public class Investigation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;

    private LocalDateTime uploadedAt;

    private int totalLogs;

    private int totalAlerts;

    private int suspiciousCount;

    private String status;

    @Column(length = 2000)
    private String description;

    public Long getId() {
        return id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public LocalDateTime getUploadedAt() {
        return uploadedAt;
    }

    public void setUploadedAt(LocalDateTime uploadedAt) {
        this.uploadedAt = uploadedAt;
    }

    public int getTotalLogs() {
        return totalLogs;
    }

    public void setTotalLogs(int totalLogs) {
        this.totalLogs = totalLogs;
    }

    public int getTotalAlerts() {
        return totalAlerts;
    }

    public void setTotalAlerts(int totalAlerts) {
        this.totalAlerts = totalAlerts;
    }

    public int getSuspiciousCount() {
        return suspiciousCount;
    }

    public void setSuspiciousCount(int suspiciousCount) {
        this.suspiciousCount = suspiciousCount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}