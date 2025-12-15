// Shared library defination. 
@Library('BaseLibraries') _
pipeline {
  // Set the agent as none so in each stage have to specify the agent.
  agent none
  stages{

    stage('Testing') {
      
      agent { label 'ums-test'} // Agents must specify in each stage.
      
      steps{ 
        echo 'Start running Automated tests.'
        script{
          cloneRepo.useEnv() //Shared library call.
        }  
      }
    }
    stage('Build the image') {
      agent {label 'docker'}
     steps {
        echo 'Start cloning the reposity'
      }
   }  
  }
}
