
# System Architecture

## 1. Overview

The Student Management System follows a client-server architecture. The backend is developed using Spring Boot, while MySQL is used for persistent data storage.

The application is containerised using Docker and deployed to Azure Kubernetes Service (AKS). Jenkins automates the CI/CD process, while Ansible automates Kubernetes deployment tasks.

## 2. Application Architecture

The application consists of the following components:

### 2.1 Client

The client sends HTTP requests to the REST API. Requests are handled by the Spring Boot application.

### 2.2 Student Controller

`StudentController` exposes REST endpoints for creating, retrieving, updating, and deleting student records.

It receives HTTP requests, processes the required operations, and returns appropriate responses.

### 2.3 Data Transfer Object

`StudentDTO` represents the student information exchanged through the API.

It separates the API request and response structure from the database entity.

### 2.4 Student Entity

`Student` represents the student record stored in the database. It is mapped to a database table using JPA.

### 2.5 Student Repository

`StudentRepository` provides database access through Spring Data JPA.

It handles persistence operations without requiring manual SQL queries for basic CRUD functionality.

### 2.6 MySQL Database

MySQL stores student records persistently. The application connects to the database using environment-based configuration.

## 3. Application Request Flow

A typical request follows this sequence:

1. The client sends an HTTP request.
2. The request reaches `StudentController`.
3. The controller processes the request and uses the repository.
4. The repository interacts with MySQL through JPA.
5. The result is converted into the appropriate API response.
6. The response is returned to the client.

## 4. CI/CD Architecture

The CI/CD workflow consists of the following stages:

1. **GitHub:** Stores the application source code and Jenkinsfile.
2. **Jenkins:** Detects source code changes and starts the pipeline.
3. **Maven:** Compiles and packages the Spring Boot application.
4. **JUnit:** Executes automated application tests.
5. **JaCoCo:** Generates code coverage reports.
6. **SonarQube:** Analyses source code quality and test coverage.
7. **Docker:** Builds a container image of the application.
8. **Azure Container Registry:** Stores the generated Docker image.
9. **Ansible:** Executes the Kubernetes deployment playbook.
10. **Azure Kubernetes Service:** Runs the application and MySQL workloads.

## 5. Deployment Architecture

The application is deployed to an Azure Kubernetes Service cluster.

The cluster contains separate Kubernetes workloads:

- **Student Management Deployment:** Runs the Spring Boot application container.
- **MySQL Deployment:** Runs the MySQL database container.
- **Application Service:** Exposes the application through a Kubernetes LoadBalancer service.
- **MySQL Service:** Provides internal network access to the database.
- **Persistent Volume Claim:** Provides persistent storage for MySQL data.
- **Kubernetes Secret:** Holds database credentials used by the MySQL deployment.

## 6. End-to-End Workflow

The complete workflow is:

```text
Developer
   |
   v
GitHub Repository
   |
   v
Jenkins Pipeline
   |
   +----------------------+
   |                      |
   v                      v
Maven Build          JUnit Tests
   |                      |
   |                      v
   |                  JaCoCo Report
   |                      |
   +----------+-----------+
              |
              v
        SonarQube
              |
              v
        Maven Package
              |
              v
        Docker Build
              |
              v
    Azure Container Registry
              |
              v
        Ansible Playbook
              |
              v
      Azure Kubernetes
         Service
              |
       +------+------+
       |             |
       v             v
   Spring Boot     MySQL
   Application    Database
```

## 7. Infrastructure and Configuration

The project maintains deployment configuration separately from application source code:

| File | Purpose |
|---|---|
| `Dockerfile` | Defines how the application image is built |
| `compose.yaml` | Defines the local application and database containers |
| `k8s/app.yaml` | Defines the application Kubernetes resources |
| `k8s/mysql.yaml` | Defines the MySQL Kubernetes resources |
| `ansible/deploy.yml` | Automates deployment to Kubernetes |
| `Jenkinsfile` | Defines the CI/CD pipeline |
| `application.properties` | Defines application and database configuration |

## 8. Summary

The architecture integrates application development, database persistence, automated testing, code quality analysis, containerisation, cloud image storage, and Kubernetes deployment.

Jenkins coordinates the CI/CD workflow, while Ansible handles deployment automation on AKS.
