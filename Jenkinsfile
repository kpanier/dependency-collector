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
                junit 'licenses-collector-service/build/test-results/test/*.xml'
            }
        }
        stage('Integration Test') {
            steps {
                sh "licenses-collector-service/gradlew -b licenses-collector-service/build.gradle integrationTest"
                junit 'licenses-collector-service/build/test-results/integrationTest/*.xml'
            }
        }
    }
}