pipeline {
    agent any

    tools {
        // Install the Maven version configured as "M3" and add it to the path.
        maven "maven_3_8_6"
    }

    stages {
        stage('Fetch') {
            steps {
                // Get some code from a GitHub repository
                git 'https://github.com/Diego-ds/expressions-backend'
            }
		}
		
		stage('Compile'){
			steps{
               // Run Maven on a Unix agent.
               //sh "mvn -Dmaven.test.failure.ignore=true clean package"

               // To run Maven on a Windows agent, use
               bat "mvn -Dmaven.test.failure.ignore=true clean package"
			   }
		}
    }
}