pipeline {
    agent {
        docker {
            image 'maven:3.8.6-openjdk-17'
            args '-v /var/run/docker.sock:/var/run/docker.sock'
        }
    }

    tools {
        allure 'Allure'
    }

    stages {
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
        stage('Gerar Allure Results') {
            steps {
                sh 'mvn allure:report'
                sh 'mvn allure:aggregate'
            }
        }
    }

    post {
        always {
            allure([
                includeProperties: false,
                jdk: '',
                properties: [],
                reportBuildPolicy: 'ALWAYS',
                results: [[path: 'target/allure-results']]
            ])
        }
    }
}