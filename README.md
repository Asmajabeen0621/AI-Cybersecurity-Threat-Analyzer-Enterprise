# 🛡️ AI-Powered Cybersecurity Threat Analyzer – Enterprise Edition

An enterprise-style cybersecurity application that analyzes security logs, detects potential threats using rule-based analysis and machine learning, matches events against local threat intelligence, and presents the results through a web-based security dashboard.

The project is designed as an academic and portfolio project demonstrating **Java, Spring Boot, MySQL, Spring Security, REST APIs, Tribuo Machine Learning, and cybersecurity concepts**.

---

## 🚀 Key Features

- 🔐 Secure login with role-based access
- 👥 ADMIN and ANALYST roles
- 📂 CSV security-log upload
- 🔎 Automated threat detection
- 🤖 Tribuo Machine Learning threat classification
- 🧠 Rule-based threat analysis
- 🌐 Offline threat-intelligence matching
- 🚨 Threat-alert generation
- 📊 Security dashboard with statistics and charts
- 🌍 IP-based threat monitoring
- 🗄️ MySQL database integration
- 🔌 REST API support
- 🛡️ CSRF and access-control security
- 💾 Persistent ML model
- 📑 Investigation and security-log management

---

## 🛠️ Technology Stack

| Technology | Purpose |
|---|---|
| Java 21 | Application development |
| Spring Boot | Backend framework |
| Spring Security | Authentication and authorization |
| Spring Data JPA | Database access |
| MySQL 8.x | Database |
| MySQL Workbench | Database management |
| Tribuo | Machine Learning |
| Thymeleaf | Web UI |
| HTML / CSS / JavaScript | Frontend |
| Chart.js | Dashboard charts |
| Maven | Build and dependency management |
| IntelliJ IDEA | Development IDE |

---

## 🏗️ System Architecture

```text
User
  ↓
Login / Authentication
  ↓
Security Dashboard
  ↓
CSV Upload
  ↓
Log Ingestion Service
  ↓
Security Log Records
  ↓
Threat Analysis Service
  ├── Rule-Based Analysis
  ├── Threat Intelligence Matching
  └── Tribuo ML Classifier
          ↓
     Threat Classification
          ↓
     Threat Alerts
          ↓
     MySQL Database
          ↓
     Dashboard / Reports
````

---

## 🔍 Threat Analysis Workflow

1. User logs into the application.
2. An authenticated user uploads a supported CSV file.
3. The application creates an investigation record.
4. CSV rows are converted into security-log records.
5. Security rules and local threat-intelligence indicators are checked.
6. Relevant features are passed to the Tribuo ML classifier when available.
7. ML results are combined with application analysis logic.
8. Threat alerts are generated and stored.
9. High-risk IP addresses can be identified for further investigation.
10. Results are displayed on the dashboard.

---

## 🤖 Machine Learning

The project uses **Tribuo** for machine-learning-based threat classification.

Example features include:

* Failed login attempts
* Phishing score
* Malware score
* Threat-intelligence match
* Event information

The trained model is stored locally:

```text
data/ml-threat-model.pb
```

Training data:

```text
src/main/resources/ml/training.csv
```

The application can use the trained model during threat analysis when it is available.

---

## 🌐 Threat Intelligence

The application includes an offline threat-intelligence mechanism.

Example indicators include:

* Suspicious IP prefixes
* Phishing keywords
* Malware keywords
* Suspicious command patterns

This allows threat analysis without depending on external threat-intelligence APIs.

---

## 🗄️ Database

The application uses **MySQL** for persistent storage.

Main database:

```text
cybersecurity_ai
```

MySQL Workbench can be used to:

* Create the database
* Execute SQL scripts
* View tables
* Inspect security logs
* View threat alerts
* Manage application data

SQL scripts are available in:

```text
sql/
```

### Example configuration

Update `application.properties` with your local MySQL credentials:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/cybersecurity_ai
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD
```

> Do not commit real database passwords to GitHub.

---

## 💻 Development Environment

The project was developed and tested using:

* **IntelliJ IDEA**
* **Java 21**
* **MySQL 8.x**
* **MySQL Workbench**
* **Maven**
* **Windows**

> The actual application development was performed in IntelliJ IDEA. The repository may contain an Eclipse checklist/documentation file, but Eclipse was not the primary development environment.

---

## ▶️ How to Run

### 1. Clone the repository

```bash
git clone https://github.com/Asmajabeen0621/AI-Cybersecurity-Threat-Analyzer-Enterprise.git
```

### 2. Open the project

Open the project in **IntelliJ IDEA**.

### 3. Configure MySQL

Create the database:

```sql
CREATE DATABASE cybersecurity_ai;
```

Configure your MySQL username and password in:

```text
src/main/resources/application.properties
```

### 4. Build the project

```bash
mvn clean install
```

### 5. Run the application

```bash
mvn spring-boot:run
```

The application runs on:

```text
http://localhost:8080
```

---

## 🔐 Demo Accounts

| Role    | Username  | Password     |
| ------- | --------- | ------------ |
| ADMIN   | `admin`   | `admin123`   |
| ANALYST | `analyst` | `analyst123` |

> These are demonstration credentials for the academic project. Change them before using the application in a real environment.

---

## 📂 Project Structure

```text
AI-Cybersecurity-Threat-Analyzer-Enterprise/
│
├── data/
│   └── ml-threat-model.pb
│
├── docs/
│   └── Project documentation
│
├── sql/
│   └── Database scripts
│
├── src/
│   └── main/
│       ├── java/
│       └── resources/
│
├── pom.xml
├── README.md
└── .gitignore
```

---

## 📊 Dashboard

The dashboard provides an overview of:

* Security logs
* Threat alerts
* High-risk events
* Blocked/suspicious IP addresses
* Threat categories
* Investigation statistics
* Threat distribution charts

The system helps users investigate uploaded security data through a centralized interface.

---

## 🔌 REST API

The application also provides REST endpoints for application operations and security-related data.

These APIs can be tested using tools such as:

* Postman
* Browser
* Frontend requests

---

## 📑 Documentation

Additional project documentation is available in:

```text
docs/
```

It includes supporting project documentation, diagrams, checklists, and related materials.

---

## 🎯 Learning Objectives

This project demonstrates practical knowledge of:

* Java and Spring Boot
* Spring Security
* MySQL and JPA
* REST API development
* Machine Learning integration
* Cybersecurity threat detection
* Threat intelligence
* File processing
* Authentication and authorization
* Dashboard development
* Software architecture
* Git and GitHub

---

## ⚠️ Disclaimer

This project is developed for **educational, demonstration, and portfolio purposes**.

It should not be used as a production cybersecurity solution without additional security testing, validation, monitoring, and deployment hardening.

---

## 👩‍💻 Author

**Asma Jabeen**

AI & Machine Learning Graduate

GitHub:
[https://github.com/Asmajabeen0621](https://github.com/Asmajabeen0621)

