pipeline {
    agent any
    options { timestamps() }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                sh '/usr/bin/mvn clean compile'
            }
        }

        stage('Test') {
            steps {
                sh '/usr/bin/mvn test'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('SonarQube') {
                    sh '''
                    /usr/bin/mvn org.sonarsource.scanner.maven:sonar-maven-plugin:5.1.0.4751:sonar \
                    -Dsonar.projectKey=student-management \
                    -Dsonar.projectName="Student Management System"
                    '''
                }
            }
        }

        stage('Package') {
            steps {
                sh '/usr/bin/mvn package -DskipTests'
            }
        }

        stage('Docker Build') {
            steps {
                sh 'docker build -t student-management:latest .'
            }
        }
    }

    post {
        success {
            echo 'CI/CD Pipeline completed successfully!'
        }
        failure {
            echo 'Pipeline failed. Check console output.'
        }
    }
}
