def build() {
    stage 'compile'
//    def maven = docker.image("maven:3.3.3-jdk-8")
//    maven.inside {
        sh './gradlew build -x test'
//    }
}

def clean_test() {
    stage 'test'
    def maven = docker.image("maven:3.3.3-jdk-8")
    maven.inside {
        sh './gradlew clean test assemble'
        step([$class: 'JUnitResultArchiver', testResults: '**/build/test-results/TEST-*.xml'])
    }
}

def push(api, user, password, org, space, domain, hostname) {
    stage 'cf push'
    sh "ls -la"
    sh "./gradlew cf-push -Pcf.ccHost=${api} -Pcf.ccUser=${user} -Pcf.ccPassword=${password} -Pcf.org=${org} -Pcf.space=${space} -Pcf.domain=${domain} -Pcf.hostName=${hostname}"
}

def pushIf(api, user, password, org, space, domain, hostname) {
    stage "cf push if acceptable"
    input "Deploy to ${org} ${space}?"
    sh "./gradlew cf-push -Pcf.ccHost=${api} -Pcf.ccUser=${user} -Pcf.ccPassword=${password} -Pcf.org=${org} -Pcf.space=${space} -Pcf.domain=${domain} -Pcf.hostName=${hostname}"
}

def runSmokeTests(url, user) {
	stage 'run smoke tests'
    sh "./gradlew cfSmokeTest -Pcf.ccHost=${api} -Pcf.ccUser=${user} -Pcf.ccPassword=${password} -Pcf.org=${org} -Pcf.space=${space} -Pcf.domain=${domain} -Pcf.hostName=${hostname}"
}

def runAcceptanceTests(url, user) {
	stage 'run acceptance tests'
    sh "./gradlew cfAcceptanceTest -Pcf.ccHost=${api} -Pcf.ccUser=${user} -Pcf.ccPassword=${password} -Pcf.org=${org} -Pcf.space=${space} -Pcf.domain=${domain} -Pcf.hostName=${hostname}"
}

return this;
