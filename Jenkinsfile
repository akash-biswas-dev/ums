@Library('base-libraries') _
pipeline {
  agent none
  stages { 
    agent {
      label 'ums-test'
    }
    stage('Testing') {
      steps{ 
        echo 'Start running Automated tests.'
        script{
          def url = env.GIT_URL
          def branch = env.GIT_BRANCH
          cloneRepo(url,branch)
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
