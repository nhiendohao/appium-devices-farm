pipeline {
    agent any

//    agent {
//        docker {
//            image 'maven:3.8.3-openjdk-11'
//            args '-v /var/run/docker.sock:/var/run/docker.sock'
//        }
//    }

    environment {
        RUN_TAGS = 'GoogleSearch'
    }

    stages {
        stage('Run Maven test') {
            steps {
                // Run your Selenium tests
                echo "Running ${BUILD_ID} on ${JENKINS_URL}"
                sh "mvn clean verify -Dgroups=${RUN_TAGS}"
            }
        }

        stage('Generate Allure report') {
            steps {
                // Install Allure command-line tool
                sh 'curl -o allure-2.23.0.zip -Ls https://repo.maven.apache.org/maven2/io/qameta/allure/allure-commandline/2.23.0/allure-commandline-2.23.0.zip'
                sh 'unzip allure-2.23.0.zip'

                // Generate Allure report
                sh './allure-2.23.0/bin/allure generate target/allure-results --clean --output allure-report'
            }

            post {
                always {
                    // Archive Allure report artifacts
                    archiveArtifacts artifacts: 'allure-report/**'

                    // Publish Allure report
                    allure([includeProperties: false, jdk: '', results: [[path: 'target/allure-results']]])
                }
            }
        }

    }
}