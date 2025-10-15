pipeline {
  agent any

  tools {
    jdk 'jdk17'
    maven 'maven3'
  }

  stages {
    stage('Checkout') {
      steps { checkout scm }
    }

    stage('Run Headless suite') {
      steps {
        sh """
          mvn -B -U \
          -Dallure.results.directory=target/allure-results \
            -Dsurefire.suiteXmlFiles=Headless.xml \
            -Dbrowser=CHROME \
            -Dheadless=true \
            clean test
        """
      }
    }

    stage('Publish Allure report') {
      steps {
        allure(results: [[path: 'allure-results']])
      }
    }
  }

  post {
    always {
      archiveArtifacts artifacts: 'target/surefire-reports/**, allure-results/**, allure-report/**', allowEmptyArchive: true
    }
  }
}
