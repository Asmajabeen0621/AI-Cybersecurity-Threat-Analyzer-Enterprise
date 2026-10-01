# AI-Powered Cybersecurity Threat Analyzer – Enterprise Edition

## What was upgraded

1. MySQL instead of H2.
2. Spring Security login.
3. ADMIN and ANALYST roles.
4. BCrypt password storage.
5. CSV log upload and batch analysis.
6. Local threat-intelligence database.
7. Real supervised ML using Tribuo Logistic Regression.
8. Smile dependency included for future model comparison.
9. ML model persistence.
10. Explainable risk features.
11. Dashboard charts.
12. REST API.
13. UML + ER diagrams.
14. Full project report.
15. Sample SQL and CSV.

Tribuo's official documentation provides `tribuo-all` 4.3.2 and classification APIs including LogisticRegressionTrainer. Smile is included as `smile-core`. MySQL Connector/J is used through Maven.

## Eclipse setup

1. Install JDK 17+.
2. Install MySQL 8.x+.
3. Create database:
   `sql/01_create_database.sql`
4. Open the project in Eclipse:
   File → Import → Existing Maven Projects.
5. Open:
   `src/main/resources/application.properties`
6. Change:
   `spring.datasource.password=CHANGE_ME`
7. Maven → Update Project.
8. Run `CyberThreatAnalyzerApplication`.
9. Open:
   `http://localhost:8080/login`

Demo accounts:
- ADMIN: admin / admin123
- ANALYST: analyst / analyst123

## CSV upload

Use `sql/sample_logs.csv`.

The administrator can upload it from the dashboard. Each record is stored and analyzed.

## ML

Click "Train Tribuo ML Model" before the first prediction if desired. The model uses the bundled training data and stores the serialized model under `data/ml-threat-model.pb`.

## Important

This is an educational defensive-security application. Automated response is simulated by creating a database record for a blocked IP. It does not alter the real operating-system firewall.

## Project documentation

- `docs/PROJECT_REPORT.md`
- `docs/UML.md`
- `docs/ER_DIAGRAM.md`
- `sql/01_create_database.sql`
- `sql/sample_logs.csv`
