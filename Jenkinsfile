pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                dir('licenses-collector-service')
                sh "./gradlew clean a"
            }
        }
        stage('Test') {
            steps {
                dir('licenses-collector-service')
                sh "./gradlew test"
            }
        }
        stage('Integration Test') {
            steps {
                dir('licenses-collector-service')
                sh "./gradlew integrationTest"
            }
        }
    }
}