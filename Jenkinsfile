
pipeline {
    agent any

    options {
        timestamps()
        disableConcurrentBuilds()
        skipDefaultCheckout(true)
        timeout(time: 30, unit: 'MINUTES')
    }

    parameters {
        choice(
            name: 'DEPLOY_ENV',
            choices: ['development', 'staging', 'production'],
            description: 'Select deployment environment'
        )

        booleanParam(
            name: 'RUN_TESTS',
            defaultValue: true,
            description: 'Run automated tests?'
        )

        string(
            name: 'RELEASE_VERSION',
            defaultValue: '1.0.0',
            description: 'Application release version'
        )
    }

    environment {
        APP_NAME = 'taskmanager'
        DOCKER_IMAGE = 'taskmanager'
    }

    stages {

        stage('Checkout') {
            steps {
                echo 'Checking out source code'
                checkout scm

                sh 'git rev-parse HEAD'
            }
        }

        stage('Build') {
            options {
                timeout(time: 10, unit: 'MINUTES')
            }

            steps {
                echo 'Building application'
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

        stage('Retry Demonstration') {
            steps {
                echo 'Demonstrating retry'

                retry(3) {
                    sh '''
                        if [ ! -f .retry-demo-counter ]; then
                            echo 0 > .retry-demo-counter
                        fi

                        count=$(cat .retry-demo-counter)
                        count=$((count + 1))
                        echo "$count" > .retry-demo-counter

                        echo "Attempt number: $count"

                        if [ "$count" -lt 3 ]; then
                            echo "Simulating temporary failure"
                            exit 1
                        fi

                        echo "Operation succeeded!"
                    '''
                }
            }
        }

        stage('Production Approval') {
            when {
                expression {
                    params.DEPLOY_ENV == 'production'
                }
            }

            steps {
                timeout(time: 5, unit: 'MINUTES') {
                    input(
                        message: "Approve deployment of ${APP_NAME} version ${params.RELEASE_VERSION} to PRODUCTION?",
                        ok: 'Approve Deployment'
                    )
                }
            }
        }

        stage('Deployment Simulation') {
            steps {
                echo "Preparing deployment to ${params.DEPLOY_ENV}"

                echo "Application: ${APP_NAME}"
                echo "Version: ${params.RELEASE_VERSION}"

                sh '''
                    echo "Simulating application deployment..."
                    sleep 5
                    echo "Deployment simulation completed!"
                '''
            }
        }

        stage('Optional Quality Check') {
            steps {
                catchError(
                    buildResult: 'UNSTABLE',
                    stageResult: 'UNSTABLE'
                ) {
                    echo 'Running optional quality check'

                    sh '''
                        echo "Simulating a non-critical quality check failure"
                        exit 1
                    '''
                }

                echo 'Pipeline continues despite the optional check failure'
            }
        }

        stage('Archive Artifact') {
            steps {
                archiveArtifacts(
                    artifacts: 'build/libs/*.jar',
                    fingerprint: true
                )
            }
        }
    }

    post {
        always {
            echo 'Pipeline execution has finished'

            junit(
                testResults: 'build/test-results/test/*.xml',
                allowEmptyResults: true
            )
        }

        success {
            echo 'Pipeline completed successfully'
        }

        failure {
            echo 'Pipeline failed. Investigate the logs.'
        }

        unstable {
            echo 'Pipeline completed, but some checks were unstable.'
        }

        cleanup {
            sh 'rm -f .retry-demo-counter'
        }
    }
}
