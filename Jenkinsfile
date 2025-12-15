// Shared library defination. 
@Library('BaseLibraries') _
pipeline {
  // Set the agent as none so in each stage have to specify the agent.
  agent none
  stages{
    environment{
      DOCKER_IMAGE_NAME = "biswasakash/ums"
      DOCKER_IMAGE_VERSION = "0.0.1"
    }

    stage('Testing') {
      
      agent { label 'ums-test'} // Agents must specify in each stage.
      
      steps{ 
        script{
          cloneRepo.useEnv() //Shared library call.
          echo 'Cloning repoitory successful.'
        }  
        echo 'Start running Automated tests.'
        dir('server'){
          sh 'mvn clean test' 
        }
      }
    }

    stage('Build the image') {
      agent {label 'docker'}
     steps {
      script{
        cloneRepo.useEnv()
        echo 'Cloning repository successful.'
      }
      sh "docker build -t ${DOCKER_IMAGE_NAME}:${DOCKER_IMAGE_VERSION} ."
      sh "docker build -t ${DOCKER_IMAGE_NAME}:latest ."
    }
   }

   stage('Deploy'){
    agent {label 'docker'}
    steps{
      echo 'Starting the application.'
    }
   }
  }
}
