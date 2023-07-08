pipeline {
    agent {
        docker {
            image 'openjdk:11.0'
        }
    }

//     environment {
//         GIT_CREDENTIALS = credentials('GIT_CREDENTIALS_ID')
//     }

    stages {
        stage('Test java version') {
            steps {
                sh 'java -version'
            }
        }

//        stage('Checkout') {
//            steps {
//                script {
//                    sh 'echo "Git credential is $GIT_CREDENTIALS"'
//                    // The below will clone your repo and will be checked out to master branch by default.
//                    git credentialsId: '15210e7b-db0e-4450-a141-138192ad79ab', url: 'https://github.com/cuongnguyen4285/selenium-junit5-allure-template.git'
//                    // Do a ls -lart to view all the files are cloned. It will be cloned. This is just for you to be sure about it.
//                    sh "ls -lart ./*"
//                    // List all branches in your repo.
//                    sh "git branch -a"
//                    // Checkout to a specific branch in your repo.
//                    sh "git checkout main"
//                }
//            }
//        }

        stage('Run Maven test') {
            steps {
                // Run your Selenium tests
                sh 'mvn clean verify -Dgroups="GoogleSearch"'
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