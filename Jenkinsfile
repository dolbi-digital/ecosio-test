pipeline {
  agent any

  tools {
    // Убери блок, если Java/Maven уже в PATH на агенте
    jdk 'jdk17'
    maven 'maven3'
  }

  stages {
    stage('Checkout') {
      steps { checkout scm }
    }

    stage('Run Chrome suite (single-thread)') {
      steps {
        sh """
          mvn -B -U \
            -Dsurefire.suiteXmlFiles=Chrome.xml \
            -Dbrowser=CHROME \
            -Dheadless=true \
            clean test
        """
      }
    }

    stage('Publish Allure report') {
      steps {
        // Нужен установленный Allure Jenkins Plugin (+ Allure Commandline в Global Tool Configuration)
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
