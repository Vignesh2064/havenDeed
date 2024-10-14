def JAVA_IMAGE_TAG // Declare the variable globally

pipeline {
    agent any
    options {
        buildDiscarder(logRotator(numToKeepStr: '5')) // Keep the last 5 builds of the pipeline itself
    }
    tools {
        jdk 'JDK17'
        maven 'Maven3'
    }
    environment {
        JAR_NAME = 'java-backend-image'
        COMPOSE_REPO_URL = 'https://github.com/Vignesh2064/cicd-docker-compose.git'
        SCANNER_HOME = tool 'Sonarqube-Scanner'
    }
    stages {
        stage('Git Checkout') {
            steps {
                git 'https://github.com/Vignesh2064/havenDeed.git'
            }
        }

        stage('Code Compile') {
            steps {
                sh "mvn clean compile"
            }
        }

        stage('Unit Tests') {
            steps {
                sh "mvn test"
            }
        }

        stage('OWASP Dependency Check') {
            steps {
                dependencyCheck additionalArguments: '--scan ./', odcInstallation: 'DP'
                dependencyCheckPublisher pattern: '**/dependency-check-report.xml'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                script {
                    withSonarQubeEnv(credentialsId: 'jenkins-sonarqube-token') { 
                        sh "mvn sonar:sonar"
                    }
                }
            }
        }

        stage('Code-Build') {
            steps {
                sh "mvn clean package"
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    // Dynamically generate JAVA_IMAGE_TAG with the current date and time
                    JAVA_IMAGE_TAG = "${new Date().format('yyyy-MM-dd-HH-mm-ss')}-build-${env.BUILD_NUMBER}"
                    sh "docker build -t ${JAR_NAME}:${JAVA_IMAGE_TAG} ."
                    
                    // Clean up old images, keeping only the last 5
                    sh """
                        IMAGES=\$(docker images --format '{{.Repository}}:{{.Tag}}' | grep ${JAR_NAME} | sort | head -n -5)
                        if [ -n "\$IMAGES" ]; then
                            echo "Removing old images:"
                            echo \$IMAGES
                            docker rmi \$IMAGES || true
                        fi
                    """
                }
            }
        }

        stage('Checkout Deployment Code') {
            steps {
                git branch: 'main', credentialsId: 'github', url: "${COMPOSE_REPO_URL}"
            }
        }
        
        stage('Update the Deployment Tags') {
            steps {
                script {
                    sh """
                        echo "Before Update:"
                        cat docker-compose.yaml
                        sed -i 's|${JAR_NAME}:.*|${JAR_NAME}:${JAVA_IMAGE_TAG}|g' docker-compose.yaml
                        echo "After Update:"
                        cat docker-compose.yaml
                    """
                }
            }
        }

        stage("Push the Changed Deployment File to Git") {
            steps {
                script {
                    sh """
                        git config --global user.name "Vignesh2064"
                        git config --global user.email "Vignesh271297@gmail.com"
                        git add docker-compose.yaml
                        git commit -m "Updated Deployment Manifest with version ${env.VERSION}"
                    """
                    withCredentials([gitUsernamePassword(credentialsId: 'github', gitToolName: 'Default')]) {
                        sh "git push https://github.com/Vignesh2064/cicd-docker-compose.git main"
                    }
                }
            }
        }


    }
}
