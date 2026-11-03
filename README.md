
# Student Management System – CI/CD Automation

## 1. Project Overview

The Student Management System is a REST API application developed using Java Spring Boot and MySQL. It provides basic CRUD (Create, Read, Update, Delete) operations for managing student records.

The project demonstrates the implementation of an automated DevOps workflow using Jenkins, Maven, SonarQube, Docker, Azure Container Registry (ACR), Azure Kubernetes Service (AKS), and Ansible.

The CI/CD pipeline automates the process of building, testing, analysing, packaging, containerising, and deploying the application.

## 2. Project Objectives

- Develop a RESTful Student Management API.
- Integrate MySQL for persistent data storage.
- Automate application builds and testing using Maven.
- Perform code quality analysis using SonarQube.
- Measure test coverage using JaCoCo.
- Containerise the application using Docker.
- Store Docker images in Azure Container Registry.
- Deploy the application to Azure Kubernetes Service.
- Automate Kubernetes deployment using Ansible.
- Implement an end-to-end CI/CD pipeline using Jenkins.

## 3. Technology Stack

| Category | Technology |
|---|---|
| Programming Language | Java 21 |
| Backend Framework | Spring Boot 3.4.13 |
| Build Tool | Maven |
| Database | MySQL |
| ORM | Spring Data JPA |
| Testing | JUnit, Spring Boot Test |
| Code Coverage | JaCoCo |
| Code Quality | SonarQube |
| CI/CD | Jenkins |
| Containerisation | Docker |
| Container Registry | Azure Container Registry |
| Container Orchestration | Azure Kubernetes Service |
| Deployment Automation | Ansible |
| Operating Environment | Ubuntu / WSL2 |

## 4. Application Features

The application provides the following REST API endpoints:

| HTTP Method | Endpoint | Description |
|---|---|---|
| GET | `/students` | Retrieve all students |
| GET | `/students/{id}` | Retrieve a student by ID |
| POST | `/students` | Add a new student |
| PUT | `/students/{id}` | Update an existing student |
| DELETE | `/students/{id}` | Delete a student |
| GET | `/students/health` | Check application health |

## 5. Project Architecture

The application follows a layered architecture:

1. **Controller:** Handles incoming HTTP requests and returns API responses.
2. **DTO:** Transfers student data between the API and application.
3. **Repository:** Handles database operations using Spring Data JPA.
4. **Entity:** Represents the student data stored in MySQL.
5. **Database:** Stores student records persistently.

## 6. CI/CD Pipeline

The Jenkins pipeline automates the following stages:

1. **Checkout:** Retrieves the latest source code from GitHub.
2. **Build:** Compiles the application using Maven.
3. **Test:** Executes automated tests and generates the JaCoCo coverage report.
4. **SonarQube Analysis:** Analyses code quality and test coverage.
5. **Package:** Generates the executable Spring Boot JAR file.
6. **Docker Build and Push:** Builds the Docker image and pushes it to Azure Container Registry.
7. **Deploy to AKS:** Uses Ansible to apply Kubernetes manifests and deploy the application.

The pipeline is configured to detect source code changes in the GitHub repository.

## 7. Deployment Architecture

The deployment workflow is:

GitHub → Jenkins → Maven → JUnit / JaCoCo → SonarQube → Docker → Azure Container Registry → Ansible → Azure Kubernetes Service

The application and MySQL database run as separate Kubernetes deployments. Kubernetes Services provide communication and application access.

## 8. Project Structure

```text
student-management-cicd/
│
├── src/
│   ├── main/
│   │   ├── java/com/example/student/
│   │   │   ├── Student.java
│   │   │   ├── StudentDTO.java
│   │   │   ├── StudentRepository.java
│   │   │   ├── StudentController.java
│   │   │   └── StudentApplication.java
│   │   │
│   │   └── resources/
│   │       └── application.properties
│   │
│   └── test/
│       └── java/com/example/student/
│           └── StudentControllerTest.java
│
├── ansible/
│   └── deploy.yml
│
├── k8s/
│   ├── app.yaml
│   └── mysql.yaml
│
├── docs/
│   └── screenshots/
│
├── Dockerfile
├── compose.yaml
├── Jenkinsfile
├── pom.xml
└── README.md
```

## 9. Running the Application Locally

### Prerequisites

- Java 21
- Maven
- MySQL

### Build the application

```bash
mvn clean package
```

### Run the application

```bash
mvn spring-boot:run
```

The application runs on:

```text
http://localhost:8080
```

### Health Check

```bash
curl http://localhost:8080/students/health
```

Configure the database connection using the environment variables defined in `application.properties`.

## 10. Running with Docker Compose

Start the application and database using:

```bash
docker compose up --build -d
```

Check running containers:

```bash
docker compose ps
```

Stop the containers:

```bash
docker compose down
```

## 11. Kubernetes Deployment

The Kubernetes manifests are available in the `k8s/` directory.

- `mysql.yaml` defines the MySQL deployment, service, secret, and persistent storage.
- `app.yaml` defines the Student Management application deployment and service.

Apply the manifests manually using:

```bash
kubectl apply -f k8s/mysql.yaml
kubectl apply -f k8s/app.yaml
```

Check deployment status:

```bash
kubectl get pods
kubectl get services
```

## 12. Ansible Automation

Ansible automates the Kubernetes deployment process.

The playbook is located at:

```text
ansible/deploy.yml
```

Execute it using:

```bash
ansible-playbook -i localhost, -c local ansible/deploy.yml
```

The playbook deploys MySQL, waits for it to become ready, deploys the application, restarts the application deployment, and checks Kubernetes resource status.

## 13. Testing and Code Coverage

Automated tests are implemented using JUnit and Spring Boot Test.

Run the tests and generate the JaCoCo report:

```bash
mvn clean test jacoco:report
```

The coverage report is generated at:

```text
target/site/jacoco/index.html
```

SonarQube is integrated into the Jenkins pipeline to analyse source code quality and test coverage.

## 14. Security and Configuration

- Database configuration is supplied through environment variables.
- Sensitive configuration is stored locally in `.env`.
- `.env` is excluded from Git using `.gitignore`.
- Jenkins credentials are used for authentication with Azure Container Registry.
- Kubernetes configuration and deployment manifests are maintained separately from application source code.

Never commit actual passwords, access tokens, or other secrets to the repository.

## 15. Future Enhancements

- Add authentication and role-based access control.
- Introduce additional student management features.
- Improve API documentation using Swagger / OpenAPI.
- Add monitoring and logging for the deployed application.
- Expand automated test coverage.

## 16. Conclusion

This project demonstrates an end-to-end DevOps workflow by integrating application development, automated testing, code quality analysis, containerisation, cloud image storage, and Kubernetes deployment.

The implementation reduces manual deployment effort and provides an automated process for delivering application updates.
