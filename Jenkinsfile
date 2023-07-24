pipeline {
    agent any

    environment {
        RUN_TAGS = "GoogleSearch"
    }

    stages {
        stage('Run Maven test') {
            steps {
                // Run your Selenium tests
                echo "Running test on ${BUILD_ID} and ${JENKINS_URL}"
                sh "mvn clean verify -Dgroups=${RUN_TAGS}"
            }
        }

        stage('Generate Allure report') {
            steps {
                // Install Allure command-line tool
                sh 'curl -o allure-2.23.0.zip -Ls https://repo.maven.apache.org/maven2/io/qameta/allure/allure-commandline/2.23.0/allure-commandline-2.23.0.zip'
                sh 'unzip -o allure-2.23.0.zip'

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

        stage('Send Slack report') {
            steps {
                sh "mvn exec:java -Dexec.mainClass=integrations.slack.Slack -Dhost=${JENKINS_URL} -DjobId=${BUILD_ID}"
            }
        }

    }
}