pipeline {
  agent any

  environment {
    DOCKERHUB_CREDENTIALS = 'dockerhub-creds'          // Jenkins credentials ID
    IMAGE_NAME = 'your-dockerhub-username/java-app'    // Replace with your repo
    IMAGE_TAG = "${env.BUILD_NUMBER}"
  }

  tools {
    maven 'Maven3'  // Must match your Maven installation name in Jenkins
  }

  stages {
    stage('Checkout Code') {
      steps {
        git 'https://github.com/your-org/java-maven-app.git'
      }
    }

    stage('Build JAR with Maven') {
      steps {
        sh 'mvn clean package'
      }
    }

    stage('Build Docker Image') {
      steps {
        script {
          sh "docker build -t $IMAGE_NAME:$IMAGE_TAG ."
        }
      }
    }

    stage('Push Docker Image to Docker Hub') {
      steps {
        withCredentials([usernamePassword(credentialsId: "$DOCKERHUB_CREDENTIALS", usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
          sh """
            echo $PASSWORD | docker login -u $USERNAME --password-stdin
            docker push $IMAGE_NAME:$IMAGE_TAG
            docker logout
          """
        }
      }
    }

    stage('Deploy to Kubernetes') {
      steps {
        script {
          // Replace with your actual Deployment name and container name
          sh """
            kubectl set image deployment/java-maven-app-deployment java-maven-app=$IMAGE_NAME:$IMAGE_TAG --record
          """
        }
      }
    }
  }

  post {
    success {
      echo "✅ Build #${env.BUILD_NUMBER} deployed successfully!"
    }
    failure {
      echo "❌ Build failed. Check the logs."
    }
  }
}