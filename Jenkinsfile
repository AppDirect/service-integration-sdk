import org.json.JSONArray
import org.json.JSONObject

@Library('jenkins-shared-library') _

/**
 * Jenkinsfile Scripted pipeline
 */

def version
pipeline {
	
    agent {
            docker {
                image "docker.appdirect.tools/appdirect/build-jdk8:latest"
                args "-v /var/run/docker.sock:/var/run/docker.sock "
        	reuseNode true    
            }
        }

    options { disableConcurrentBuilds() }
	environment {
		GITHUB_REPO_NAME = 'service-integration-sdk'
		GITHUB_REPO_OWNER = 'AppDirect'
		SONAR_SERVER_NAME = 'Sonar v2'
		MAVEN_CONFIGURATION_FILE = 'MavenArtifactorySettingsCustomFile'
		PR_BRANCH_REGEX = /(pr|PR)-\d+$/
		DOCKER_REGISTRY = 'docker.appdirect.tools'
	}

	stages {
		stage('PR approval') {
		        steps {
				script {
					if (BRANCH_NAME != 'master' || BRANCH_NAME != 'release-v1') {	 
						timeout(time: 15, unit: "MINUTES") {
						   input message: 'Do you want to approve the PR build?', ok: 'Yes'
						}
					 }
				}
			}
		}
		stage('Checkout') {
		        steps {
			      
				echo 'Checking out from repository...'
				checkout scm: [
						$class           : 'GitSCM',
						branches         : scm.branches,
						userRemoteConfigs: scm.userRemoteConfigs,
						extensions       : [
								[$class: 'CloneOption', noTags: false],
								[$class: 'LocalBranch', localBranch: "master"]
						]
				]
				script {
					if (env.BRANCH_NAME == "release-v1") {
						version = getSemver('release-v1', '', env.BRANCH_NAME != 'release-v1' ? '-SNAPSHOT' : '')
					} else {
						version = getSemver('master', '', env.BRANCH_NAME != 'master' ? '-SNAPSHOT' : '')
					}
				}
			}
		}

		stage('Setup') {
                        when {
			   expression { BRANCH_NAME ==~ /(release-v1|master)/ }
			}
			
			steps {
				echo 'Prepare Maven properties'
				configFileProvider(
						[configFile(fileId: MAVEN_CONFIGURATION_FILE, variable: 'MAVEN_SETTINGS')]) {
					sh 'cp $MAVEN_SETTINGS mavenSettings'
				}
				withCredentials([string(credentialsId: 'gpg-passphrase', variable: 'GPG_PASSPHRASE'),
					         usernamePassword(credentialsId: 'sdk-ossrh', passwordVariable: 'OSSRH_PASSWORD', usernameVariable: 'OSSRH_USERNAME')]) {
					sh 'src/script/addkeys.sh'
				}

		    }
		}

		stage('Versioning') {
			steps {
				script {
					echo 'Setting build version...'
					sh "./mvnw versions:set -DnewVersion=${version} -f 'pom.xml' -s settings.xml | grep -v 'Props:'"
				}
			}
		}

		stage('Build') {
			steps {

				echo 'Building project...'
				withCredentials([file(credentialsId: 'gpg-private-key', variable: 'GPG_KEY')]) {		
					
					withPullRequestBranch {
						sh '''
						   ./mvnw install source:jar-no-fork
						   '''
					}
					script {
						
						if (BRANCH_NAME == 'master' || BRANCH_NAME == 'release-v1') {
							sh "gpg2 --batch --no-tty --import $GPG_KEY || /bin/true"
							sh '''
							    ./mvnw deploy source:jar-no-fork -Prelease -U -s settings.xml
						           '''		
						}
					}
				}
			}
		}

		stage('SonarQube') {
			steps {
				sonarScanner version
			}
		}

		stage('Release scope') {
			when {
				expression { BRANCH_NAME ==~ /(release-v1|master)/ }
			}
			steps {
				pushGitTag version
			}
		}
	}
}
