
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
                sh '/usr/bin/mvn test jacoco:report'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('SonarQube') {
                    sh '''
                        /usr/bin/mvn org.sonarsource.scanner.maven:sonar-maven-plugin:5.1.0.4751:sonar \
                        -Dsonar.projectKey=student-management \
                        -Dsonar.projectName="Student Management System"
			-Dsonar.coverage.jacoco.xmlReportPaths=target/site/jacoco/jacoco.xml
                    '''
                }
            }
        }

        stage('Package') {
            steps {
                sh '/usr/bin/mvn package -DskipTests'
            }
        }

        stage('Docker Build and Push to ACR') {
            steps {
                withCredentials([usernamePassword(
                    credentialsId: 'acr-credentials',
                    usernameVariable: 'ACR_USER',
                    passwordVariable: 'ACR_PASS'
                )]) {
                    sh '''
                        docker build -t student-management:latest .
                        docker tag student-management:latest studentmgmtacr27901.azurecr.io/student-management:latest

                        echo "$ACR_PASS" | docker login studentmgmtacr27901.azurecr.io -u "$ACR_USER" --password-stdin

                        docker push studentmgmtacr27901.azurecr.io/student-management:latest

                        docker logout studentmgmtacr27901.azurecr.io
                    '''
                }
            }
        }

        stage('Deploy to AKS using Ansible') {
            steps {
                echo 'Deploying application to AKS using Ansible'
                sh '''
                    cd "$WORKSPACE"
                    ansible-playbook -i localhost, -c local ansible/deploy.yml
                '''
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
