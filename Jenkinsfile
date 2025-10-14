pipeline {
  agent { label 'linux || mac || any' } // заміни на свій лейбл агента

  options {
    timestamps()
    ansiColor('xterm')
    buildDiscarder(logRotator(numToKeepStr: '20'))
  }

  tools {
    // Якщо налаштовані в Global Tool Configuration. Інакше видали цей блок.
    jdk 'jdk17'
    maven 'maven3'
  }

  environment {
    MAVEN_OPTS = '-Djava.awt.headless=true'
  }

  stages {
    stage('Checkout') {
      steps {
        checkout scm
      }
    }

    stage('Tests headless (matrix CHROME/FIREFOX/EDGE)') {
      matrix {
        axes {
          axis {
            name 'BROWSER'
            values 'CHROME', 'FIREFOX', 'EDGE'
          }
        }
        stages {
          stage('Run TestNG suite') {
            steps {
              sh """
                mvn -B -U \
                  -Dbrowser=${BROWSER} \
                  -Dheadless=true \
                  -Dsurefire.suiteXmlFiles=headless.xml \
                  clean test
              """
            }
          }

          stage('Attach env info') {
            steps {
              sh '''
                mkdir -p allure-results
                echo "Environment=CI" > allure-results/environment.properties
                echo "Browser='${BROWSER} (headless)'" >> allure-results/environment.properties
                echo "Build=${BUILD_NUMBER}" >> allure-results/environment.properties
                echo "Executor=Jenkins" >> allure-results/environment.properties
                echo "OS=$(uname -s)" >> allure-results/environment.properties
              '''
            }
          }

          stage('Stash results') {
            steps {
              stash name: "allure-${BROWSER}", includes: 'allure-results/**', allowEmpty: true
              stash name: "junit-${BROWSER}",  includes: 'target/surefire-reports/*.xml', allowEmpty: true
            }
          }
        }
      }
    }

    stage('Publish reports') {
      steps {
        // зібрати Allure із усіх гілок матриці
        sh 'rm -rf merged-allure && mkdir -p merged-allure'
        script {
          ['CHROME','FIREFOX','EDGE'].each { b ->
            dir("merged-allure/${b}") {
              unstash "allure-${b}"
            }
          }
        }

        // Allure (потрібен Allure Jenkins Plugin + налаштований tool)
        allure(
          includeProperties: false,
          jdk: '',
          results: [
            [path: 'merged-allure/CHROME'],
            [path: 'merged-allure/FIREFOX'],
            [path: 'merged-allure/EDGE']
          ]
        )

        // JUnit тренд у Jenkins
        dir('junit-merged') {
          script {
            ['CHROME','FIREFOX','EDGE'].each { b ->
              dir(b) { unstash "junit-${b}" }
            }
          }
        }
        junit testResults: 'junit-merged/**/target/surefire-reports/*.xml', allowEmptyResults: true
      }
    }
  }

  post {
    always {
      archiveArtifacts artifacts: 'target/surefire-reports/**, allure-results/**, merged-allure/**', allowEmptyArchive: true
    }
  }
}
