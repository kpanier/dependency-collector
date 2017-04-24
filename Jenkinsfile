pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                sh "licenses-collector-service/gradlew -b licenses-collector-service/build.gradle clean a"
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