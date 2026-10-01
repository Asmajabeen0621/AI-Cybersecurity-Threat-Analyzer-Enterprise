# AI-POWERED CYBERSECURITY THREAT ANALYZER
## Java Enterprise Application – Project Report

### 1. Abstract
The AI-Powered Cybersecurity Threat Analyzer is a Java-based security monitoring application designed to collect system logs, classify suspicious activity, provide explainable risk scores, and support analyst investigation. The upgraded version uses Spring Boot, Spring Security, MySQL, Tribuo/Smile-compatible machine-learning components, CSV ingestion, offline threat intelligence, role-based access control and dashboard visualization.

### 2. Problem Statement
Traditional log monitoring produces large volumes of events and requires analysts to manually identify suspicious patterns. The proposed system automates first-level analysis by extracting behavioral and textual features from security logs and applying a local machine-learning classifier together with rule-based threat-intelligence indicators.

### 3. Objectives
- Collect and store security logs.
- Authenticate users securely.
- Separate ADMIN and ANALYST permissions.
- Import large batches of logs through CSV.
- Train and execute a local ML classification model.
- Detect phishing, malware and anomalous behavior.
- Match events against offline threat intelligence.
- Generate explainable alerts.
- Visualize security activity.
- Record automated containment actions.

### 4. Technology Stack
Frontend: HTML, CSS, JavaScript, Chart.js
Backend: Java 17, Spring Boot
Security: Spring Security, BCrypt
Database: MySQL
ORM: Spring Data JPA / Hibernate
ML: Tribuo Logistic Regression; Smile dependency included for future comparative models
Build: Maven
IDE: Eclipse
No external threat-analysis API is required.

### 5. Architecture
Browser → Spring Security → Controller → Service Layer → ML/Threat Intelligence → JPA → MySQL.

### 6. Machine Learning
The included training dataset contains numerical features:
- failedAttempts
- phishingScore
- malwareScore
- intelMatch

The target label is LOW, MEDIUM, HIGH or CRITICAL. Tribuo LogisticRegressionTrainer trains a classification model. The trained model is serialized locally and reused for prediction.

This is a genuine supervised ML component, but the bundled dataset is intentionally small for demonstration. For a final production/academic evaluation, replace it with a larger labeled cybersecurity dataset and report accuracy, precision, recall, F1-score and confusion matrix.

### 7. Authentication and Authorization
Spring Security protects the application. Users are stored in MySQL with BCrypt password hashes and a role of ADMIN or ANALYST. ADMIN routes include CSV import, ML training and threat-intelligence administration. Analysts can access monitoring and investigation functionality.

### 8. CSV Log Processing
Expected CSV columns:
username, ipAddress, eventType, failedAttempts, country, message

Each row is stored, transformed into ML features, classified, and converted into a threat alert.

### 9. Threat Intelligence
Threat intelligence is stored locally in MySQL. This makes the system usable offline and avoids dependence on external APIs. Indicators can represent phrases, command names, IP prefixes or other local signatures.

### 10. Explainable AI
Each alert stores:
- ML prediction
- risk score
- feature values
- threat category
- matched intelligence indicator
This allows an analyst to understand the basis of the alert rather than seeing only a classification label.

### 11. Automated Response
High and critical alerts create a blocked-IP record. In this educational version the action is recorded in MySQL rather than modifying the operating system firewall.

### 12. Testing Plan
Test authentication with valid/invalid credentials.
Test role restrictions for ADMIN and ANALYST.
Upload valid and malformed CSV files.
Test phishing messages.
Test malware commands.
Test repeated failed logins.
Verify threat-intelligence matches.
Verify ML model training and persistence.
Verify dashboard counts and alert records.

### 13. Expected Results
The application should provide a centralized security dashboard, identify suspicious events, classify them by severity, preserve an explanation for each decision, and give analysts a workflow for reviewing the generated alerts.

### 14. Limitations
The bundled training dataset is educational and small. The system is not a replacement for a commercial SIEM/EDR platform. Real firewall changes, live packet capture and external threat feeds are deliberately excluded from this safe academic implementation.

### 15. Future Enhancements
- Larger labeled security dataset.
- Random Forest / XGBoost comparison.
- True anomaly-detection model.
- Model evaluation page.
- Confusion matrix and ROC/PR metrics.
- Real-time WebSocket alert stream.
- Audit logs.
- Password reset.
- MFA.
- Docker deployment.
- MySQL backup and recovery.
- SIEM/syslog ingestion.
- Case management and analyst notes.

### 16. Conclusion
The upgraded system demonstrates how Java enterprise development, database engineering, cybersecurity rules and machine learning can be combined into a practical security-monitoring application. Its modular architecture allows future models, datasets and response mechanisms to be added without redesigning the complete application.
