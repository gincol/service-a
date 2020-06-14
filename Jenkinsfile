def BRANCH = ""

pipeline {

	agent { label "debian" }

	stages {

		stage ("Inicializacion") {
            steps {
				echo "Inicio pipeline"
            }
        }

        stage ("artifactId y version desde el pom.xml") {
            steps {
            	script {
    				BRANCH = BRANCH_NAME.substring(BRANCH_NAME.lastIndexOf('/') + 1, BRANCH_NAME.length()-1)
    				pom = readMavenPom file: 'pom.xml'
    				appName = pom.artifactId.toLowerCase()
    				artefacto = "${appName}"
    				appName = "${appName}${sprint}"
    				imageNameApp = appName.toLowerCase()
					imageTagApp = pom.version
            	}
            }
        }

        stage ("Echo variables") {
            steps {
				echo "imageTagApp = ${imageTagApp}"
				echo "imageNameApp = ${imageNameApp}"
				echo "appName = ${appName}"
				echo "artefacto = ${artefacto}"
				echo "appName = ${appName}"
				echo "BRANCH_NAME = ${BRANCH}"
            }
        }

        stage("deploy") {
        	steps {
				script {
					echo "desplegando"
		        }
	        }
	    }

    }

	post {
        always {
			echo "Fin Proceso"
        }
        success {
            echo "Fin OK";
        }
        unstable {
            echo "Fin inestable";
        }
        failure {
            echo "Fin KO";
        }
    }
}
