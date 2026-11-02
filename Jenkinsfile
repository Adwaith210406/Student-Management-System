pipeline {
    agent any

    options {
        timestamps()
    }

    stages {

        stage('Checkout') {
            steps {
                echo 'Checking out source code from GitHub'
                checkout scm
            }
        }

        stage('Build') {
            steps {
                echo 'Compiling the Java application'
                sh '/usr/bin/mvn clean compile'
            }
        }

        stage('Test') {
            steps {
                echo 'Running automated JUnit tests'
                sh '/usr/bin/mvn test'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                echo 'Analysing code quality'
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
                echo 'Packaging the application'
                sh '/usr/bin/mvn package -DskipTests'
            }
        }
    }

    post {
        success {
            echo 'CI Pipeline completed successfully!'
        }

        failure {
            echo 'CI Pipeline failed. Check the console output.'
        }
    }
}
