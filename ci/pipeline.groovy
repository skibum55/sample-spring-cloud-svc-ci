def build() {
//    def maven = docker.image("maven:3.3.3-jdk-8")
//    maven.inside {
        sh './gradlew --full-stacktrace build -x test'
//    }
}

def clean_test() {
//    def maven = docker.image("maven:3.3.3-jdk-8")
//    maven.inside {
        sh './gradlew --full-stacktrace clean test assemble'
        step([$class: 'JUnitResultArchiver', testResults: '**/build/test-results/TEST-*.xml'])
        dir('build/libs') {stash name: 'jar', includes: 'sample-spring-cloud-svc-ci-1.0.0-SNAPSHOT.jar'}
        try {
            sh './gradlew upload'
        } catch (Error _) {
            echo 'Nexus not running -- check docker compose file'
        }
//    }
}

def sonar(url) {
  try {
      sh "./gradlew sonarqube -Dsonar.host.url=${url} -Dsonar.verbose=true"
  } catch (Error _) {
      echo 'Sonar is not running -- check docker compose file'
  }
}

def push(api, user, password, org, space, domain, hostname) {
    sh "ls -la"
    sh "./gradlew --full-stacktrace cf-push -Pcf.ccHost=${api} -Pcf.ccUser=${user} -Pcf.ccPassword=${password} -Pcf.org=${org} -Pcf.space=${space} -Pcf.domain=${domain} -Pcf.hostName=${hostname}"
}

def pushIf(api, user, password, org, space, domain, hostname) {
    input "Deploy to ${org} ${space}?"
    sh "./gradlew --full-stacktrace cf-push -Pcf.ccHost=${api} -Pcf.ccUser=${user} -Pcf.ccPassword=${password} -Pcf.org=${org} -Pcf.space=${space} -Pcf.domain=${domain} -Pcf.hostName=${hostname}"
}

def runSmokeTests(api, user, password, org, space, domain, hostname) {
    dir('build/libs') {unstash name:'jars'}
    sh "./gradlew --full-stacktrace cfSmokeTest -Pcf.ccHost=${api} -Pcf.ccUser=${user} -Pcf.ccPassword=${password} -Pcf.org=${org} -Pcf.space=${space} -Pcf.domain=${domain} -Pcf.hostName=${hostname}"
}

def runAcceptanceTests(api, user, password, org, space, domain, hostname) {
    dir('build/libs') {unstash name:'jars'}
    sh "./gradlew --full-stacktrace cfAcceptanceTest -Pcf.ccHost=${api} -Pcf.ccUser=${user} -Pcf.ccPassword=${password} -Pcf.org=${org} -Pcf.space=${space} -Pcf.domain=${domain} -Pcf.hostName=${hostname}"
}

return this;
