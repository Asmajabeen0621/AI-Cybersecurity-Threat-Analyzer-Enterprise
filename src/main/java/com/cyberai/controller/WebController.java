package com.cyberai.controller;

import com.cyberai.model.*;
import com.cyberai.repository.*;
import com.cyberai.service.*;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class WebController {

    private final SecurityLogRepository logs;
    private final ThreatAlertRepository alerts;
    private final ThreatIntelRepository intel;
    private final BlockedIpRepository blocked;
    private final InvestigationRepository investigations;
    private final LogIngestionService ingestion;
    private final MlThreatClassifier ml;

    public WebController(
            SecurityLogRepository logs,
            ThreatAlertRepository alerts,
            ThreatIntelRepository intel,
            BlockedIpRepository blocked,
            InvestigationRepository investigations,
            LogIngestionService ingestion,
            MlThreatClassifier ml) {

        this.logs = logs;
        this.alerts = alerts;
        this.intel = intel;
        this.blocked = blocked;
        this.investigations = investigations;
        this.ingestion = ingestion;
        this.ml = ml;
    }

    // =====================================================
    // LOGIN
    // =====================================================

    @GetMapping("/login")
    String login() {
        return "login";
    }

    @GetMapping("/access-denied")
    String accessDenied() {
        return "access-denied";
    }

    // =====================================================
    // DASHBOARD
    // =====================================================

    @GetMapping("/")
    String dashboard(Model m) {

        m.addAttribute(
                "logs",
                logs.findTop25ByOrderByTimestampDesc()
        );

        m.addAttribute(
                "alerts",
                alerts.findTop25ByOrderByDetectedAtDesc()
        );

        m.addAttribute(
                "investigations",
                investigations.findAllByOrderByUploadedAtDesc()
        );

        m.addAttribute(
                "stats",
                Map.of(
                        "logs", logs.count(),
                        "alerts", alerts.count(),
                        "blocked", blocked.count(),
                        "intel", intel.count()
                )
        );

        return "dashboard";
    }

    // =====================================================
    // INVESTIGATION DETAILS
    // =====================================================

    @GetMapping("/investigations/{id}")
    String investigationDetails(
            @PathVariable Long id,
            Model m) {

        // Fetch the investigation
        Investigation investigation =
                investigations.findById(id).orElse(null);

        // Redirect to dashboard if investigation does not exist
        if (investigation == null) {
            return "redirect:/";
        }

        // Fetch logs belonging to this investigation
        List<SecurityLog> investigationLogs =
                logs.findAll()
                        .stream()
                        .filter(log ->
                                id.equals(log.getInvestigationId()))
                        .collect(Collectors.toList());

        // Fetch alerts belonging to this investigation
        List<ThreatAlert> investigationAlerts =
                alerts.findAll()
                        .stream()
                        .filter(alert ->
                                id.equals(alert.getInvestigationId()))
                        .collect(Collectors.toList());

        // Pass investigation data to Thymeleaf
        m.addAttribute(
                "investigation",
                investigation
        );

        m.addAttribute(
                "logs",
                investigationLogs
        );

        m.addAttribute(
                "alerts",
                investigationAlerts
        );

        // IMPORTANT:
        // The HTML template is named investigation-details.html
        // Therefore, return the view name without .html

        return "investigation-details";
    }

    // =====================================================
    // IP INVESTIGATION PROFILE
    // =====================================================

    @GetMapping("/ip-investigation/{ipAddress}")
    String ipInvestigation(
            @PathVariable String ipAddress,
            Model m) {

        List<SecurityLog> ipLogs =
                logs.findByIpAddressOrderByTimestampDesc(ipAddress);

        List<ThreatAlert> ipAlerts =
                alerts.findByIpAddressOrderByDetectedAtDesc(ipAddress);

        long totalLogs =
                logs.countByIpAddress(ipAddress);

        long totalAlerts =
                alerts.countByIpAddress(ipAddress);

        long unresolvedAlerts =
                alerts.countByIpAddressAndResolvedFalse(ipAddress);

        boolean isBlocked =
                blocked.existsByIpAddress(ipAddress);

        BlockedIp blockedDetails =
                blocked.findByIpAddress(ipAddress).orElse(null);

        int highestRiskScore =
                ipAlerts.stream()
                        .mapToInt(ThreatAlert::getRiskScore)
                        .max()
                        .orElse(0);

        String riskLevel;

        if (highestRiskScore >= 80) {

            riskLevel = "CRITICAL";

        } else if (highestRiskScore >= 60) {

            riskLevel = "HIGH";

        } else if (highestRiskScore >= 30) {

            riskLevel = "MEDIUM";

        } else {

            riskLevel = "LOW";
        }

        m.addAttribute(
                "ipAddress",
                ipAddress
        );

        m.addAttribute(
                "ipLogs",
                ipLogs
        );

        m.addAttribute(
                "ipAlerts",
                ipAlerts
        );

        m.addAttribute(
                "totalLogs",
                totalLogs
        );

        m.addAttribute(
                "totalAlerts",
                totalAlerts
        );

        m.addAttribute(
                "unresolvedAlerts",
                unresolvedAlerts
        );

        m.addAttribute(
                "isBlocked",
                isBlocked
        );

        m.addAttribute(
                "blockedDetails",
                blockedDetails
        );

        m.addAttribute(
                "highestRiskScore",
                highestRiskScore
        );

        m.addAttribute(
                "riskLevel",
                riskLevel
        );

        return "ip-investigation";
    }

    // =====================================================
    // CSV IMPORT
    // =====================================================

    @PostMapping("/admin/import")
    String importCsv(
            @RequestParam("file") MultipartFile f)
            throws Exception {

        ingestion.importCsv(f);

        return "redirect:/";
    }

    // =====================================================
    // ML TRAINING
    // =====================================================

    @PostMapping("/admin/train")
    String train(
            RedirectAttributes redirectAttributes)
            throws Exception {

        ml.train();

        redirectAttributes.addFlashAttribute(
                "successMessage",
                "✅ Tribuo ML Model trained successfully!"
        );

        return "redirect:/";
    }

    // =====================================================
    // THREAT INTELLIGENCE
    // =====================================================

    @GetMapping("/admin/intel")
    String intel(Model m) {

        m.addAttribute(
                "items",
                intel.findAll()
        );

        return "intel";
    }
}