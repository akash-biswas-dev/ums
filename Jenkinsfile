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
          // This variables are passed by jenkins it self.
          def url = env.GIT_URL
          def branch = env.GIT_BRANCH
          echo "${url} and ${branch}"
          // cloneRepo(url,branch) // Shared library call.
        
        }  
      }
    }
    stage('Build the image') {
     steps {
        echo 'Start cloning the reposity'
      }
   }  
  }
}
