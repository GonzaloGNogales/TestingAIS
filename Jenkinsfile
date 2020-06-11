pipeline {
   tools {
     maven "M3"
   }
   agent any
   stages {
     stage("Preparation") { 
       steps {
         git(
         	url:  'https://github.com/GonzaloGNogales/TestingAIS.git',
         	credentialsId: 'developer',
         	branch: 'master'
         )
       }
     }
     stage("Test") {
       steps {
         script{
         if(isUnix()) {
			sh "./mvnw test"
		} else {
			bat(/mvnw.cmd test/)
		}
		}
       }
     }
   } 
   post {
      always {
	    junit "TestingAIS/**/target/surefire-reports/TEST-*.xml"
      }
   }
}