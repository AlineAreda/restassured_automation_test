pipeline {
    agent {
        docker {
            image 'maven:3.8.6-openjdk-17'
            args '-v /var/run/docker.sock:/var/run/docker.sock'
        }
    }

    environment {
        ALLURE_RESULTS = 'target/allure-results'
        ALLURE_REPORT = 'target/allure-report'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        stage('Build') {
            steps {
                sh 'mvn clean install'
            }
        }
        stage('Testes API') {
            steps {
                sh 'mvn test'
            }
        }
        stage('Gerar Relatório Allure') {
            steps {
                sh 'mvn allure:aggregate'
                sh 'mvn allure:report'
            }
        }
    }

    post {
        always {
            script {
                allure([
                    includeProperties: false,
                    reportBuildPolicy: 'ALWAYS',
                    results: [[path: "${ALLURE_RESULTS}"]]
                ])
            }
        }
    }
}
