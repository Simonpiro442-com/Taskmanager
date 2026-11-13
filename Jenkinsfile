pipeline {
    agent any

    options {
        timestamps()
        disableConcurrentBuilds()
        skipDefaultCheckout(true)
    }

    parameters {
        choice(
            name: 'DEPLOY_ENV', 
            choices: ['development', 'staging', 'production'], 
            description: 'Select the target environment'
        )

        booleanParam(
            name: 'RUN_TESTS',
            defaultValue: 'true',
            description: 'Should automated test runs?'
        )

        string(
            name: 'RELEASE_VERSION',
            defaultValue: '1.0.0',
            description: 'Application release version'
        )
    }

    environment {
        APP_NAME: 'taskmanager'
        DOCKER_IMAGE: 'taskmanager'
    }

    stages {

        stage('Checkout') {
            steps {
                echo 'Checking our Task Manager source code'
                checkout scm

                echo "Build_Number: ${env.BUILD_NUMBER}"
                echo "Job Name: ${env.JOB_NAME}"
                echo "Workspace: ${env.WORKSPACE}"
                echo "Git Commit: ${env.GIT_COMMIT}"

            }
        }

        stage('Display Configuration') (
            steps {
                echo "Application: ${env.APP_NAME}"
                echo "Docker Image: ${env.DOCKER_IMAGE}"

                echo "Deployment Environment: ${params.DEPLOY_ENV}"
                echo "Release Version: ${params.RELEASE_VERSION}"
                echo "Run Tests: ${params.RUN_TESTS}"
            }
        )

        stage('Verify Environment') {
            steps {
                sh 'java -version'
                sh './gradlew --version'
            }
        }

        stage('Build') {
            steps {
                echo 'Building Task Manager'

                sh './gradlew clean assemble'
            }
        }

        stage('Test') {
            when {
                expression {
                    params.RUN_TESTS
                }
            }

            steps {
                echo 'Running automated tests'
                sh './gradlew test'
            }
        }

        stage('Archive Artifact') {
            steps {
                archiveArtifacts(
                    artifacts: 'build/libs/*.jar,build/libs/*.war',
                    allowEmptyArchive: false,
                    fingerprint: true
                )
            }
        }
    }

    post (
        always {
            junit(
                testResults: 'build/test-results/test/*.xml',
                allowEmptyResults: true
            )

            echo 'Pipeline execution completed'
        }

        success {
            echo "Task Manager CI pipeline completed successfully"
        }

        failure {
            echo "Task manager CI pipeline failed"
        }
    )
}