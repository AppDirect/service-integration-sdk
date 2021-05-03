import org.json.JSONArray
import org.json.JSONObject

@Library('jenkins-shared-library') _

/**
 * Jenkinsfile Scripted pipeline
 */

def version
pipeline {
agent any
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
					version = getSemver('master', '', env.BRANCH_NAME != 'master' ? '-SNAPSHOT' : '')
				}
			}
		}

		stage('Setup') {
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
					sh "gpg2  --no-tty --import $GPG_KEY || /bin/true"
					withPullRequestBranch {
						sh "./mvnw install source:jar-no-fork -Prelease -U -s settings.xml"
					}
					withMasterBranch {
						sh "./mvnw deploy source:jar-no-fork -Prelease -U -s settings.xml"
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
			steps {
				withMasterBranch {
					pushGitTag version
				}
			}
		}
	}
}
