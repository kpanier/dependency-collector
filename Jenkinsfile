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
                sh "licenses-collector-service/gradlew -b licenses-collector-service/build.gradle test"
            }
        }
        stage('Integration Test') {
            steps {
                sh "licenses-collector-service/gradlew -b licenses-collector-service/build.gradle integrationTest"
            }
        }
    }
}