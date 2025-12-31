pipeline {
  agent any
  
  tools {
    maven 'Maven'
    jdk 'JDK21'
  }
  
  environment {
    MAVEN_OPTS = '-Xmx1024m'
  }
  
  stages {
    stage('Checkout') {
      steps {
        checkout scm
      }
    }
    
    stage('Build & Compile') {
      steps {
        sh 'mvn -B clean compile'
      }
    }
    
    stage('Unit Tests') {
      steps {
        sh 'mvn -B test'
      }
      post {
        always {
          junit 'target/surefire-reports/**/*.xml'
          archiveArtifacts artifacts: 'target/surefire-reports/**/*', allowEmptyArchive: true
        }
      }
    }
    
    stage('Code Coverage') {
      steps {
        sh 'mvn -B jacoco:report'
        publishHTML([
          target: [
            allowMissing: false,
            alwaysLinkToLastBuild: true,
            keepAll: true,
            reportDir: 'target/site/jacoco',
            reportFiles: 'index.html',
            reportName: 'JaCoCo Coverage Report'
          ]
        ])
      }
    }
    
    stage('SonarQube Analysis') {
      environment {
        SONAR_HOST_URL = credentials('SONAR_HOST_URL')
        SONAR_TOKEN = credentials('SONAR_TOKEN')
      }
      steps {
        withSonarQubeEnv('SonarQube') {
          sh '''
            mvn -B verify sonar:sonar \
              -Dsonar.projectKey=Sara542-source_VottingApp \
              -Dsonar.projectName="Voting App" \
              -Dsonar.sources=src/main/java \
              -Dsonar.tests=src/test/java \
              -Dsonar.java.binaries=target/classes \
              -Dsonar.java.test.binaries=target/test-classes \
              -Dsonar.coverage.jacoco.xmlReportPaths=target/site/jacoco/jacoco.xml \
              -Dsonar.junit.reportPaths=target/surefire-reports
          '''
        }
      }
    }
    
    stage('Quality Gate') {
      steps {
        timeout(time: 15, unit: 'MINUTES') {
          waitForQualityGate abortPipeline: true
        }
      }
    }
    
    stage('Package') {
      steps {
        sh 'mvn -B package -DskipTests'
        archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
      }
    }
  }
  
  post {
    always {
      echo 'Pipeline completed'
      // cleanWs()
    }
    success {
      echo 'Pipeline succeeded!'
    }
    failure {
      echo 'Pipeline failed!'
    }
  }
}