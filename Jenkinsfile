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
        sh 'rm -rf allure-results target/allure-results || true'
          mvn -B -U \
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
