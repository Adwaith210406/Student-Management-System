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
