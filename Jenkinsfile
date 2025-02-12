pipeline {
    agent {
        docker {
            image 'maven:3.8.6-openjdk-17' // Imagem corrigida
            args '-v /var/run/docker.sock:/var/run/docker.sock'
        }
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
                sh 'mvn allure:report' // Gera o relatório Allure
                sh 'mvn allure:aggregate' // Agrega os resultados (opcional)
            }
        }
    }

    post {
        always {
            node {
                script {
                    // Verifica se o diretório de resultados existe
                    if (fileExists('target/allure-results')) {
                        allure([
                            includeProperties: false,
                            reportBuildPolicy: 'ALWAYS',
                            results: [[path: 'target/allure-results']]
                        ])
                    } else {
                        echo 'Diretório de resultados do Allure não encontrado. Relatório não gerado.'
                    }
                }
            }
        }
    }
}