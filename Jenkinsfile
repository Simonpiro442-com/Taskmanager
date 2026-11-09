pipeline {
    agent any

    options {
        timestamps()
        disableConcurrentBuilds()
    }

    stages {

        stage('Checkout') {
            steps {
                echo 'Checking out Task Manager source code'
                checkout scm
            }
        }

        stage('Verify Environment') {
            steps {
                echo 'Checking Java and Gradle versions'

                sh 'java -version'

                sh 'chmod +x gradlew'

                sh './gradlew --version'
            }
        }

        stage('Build and Test') {
            steps {
                echo 'Building and testing Spring Boot application'

                sh './gradlew clean build'
            }
        }

        stage('Archive Artifact') {
            steps {
                echo 'Archiving application artifact'

                archiveArtifacts(
                    artifacts: 'build/libs/*.jar,build/libs/*.war',
                    allowEmptyArchive: false,
                    fingerprint: true
                )
            }
        }
    }

    post {
        always {
            echo 'Pipeline execution completed'

            junit(
                testResults: 'build/test-results/test/*.xml',
                allowEmptyResults: true
            )
        }

        success {
            echo 'Task Manager CI pipeline completed successfully!'
        }

        failure {
            echo 'Task Manager CI pipeline failed. Check the logs.'
        }
    }
}
