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
//    }
}

def push(api, user, password, org, space, domain, hostname) {
    sh "ls -la"
    sh "./gradlew --full-stacktrace cf-push -Pcf.ccHost=${api} -Pcf.ccUser=${user} -Pcf.ccPassword=${password} -Pcf.org=${org} -Pcf.space=${space} -Pcf.domain=${domain} -Pcf.hostName=${hostname}"
}

def pushIf(api, user, password, org, space, domain, hostname) {
    input "Deploy to ${org} ${space}?"
    sh "./gradlew --full-stacktrace cf-push -Pcf.ccHost=${api} -Pcf.ccUser=${user} -Pcf.ccPassword=${password} -Pcf.org=${org} -Pcf.space=${space} -Pcf.domain=${domain} -Pcf.hostName=${hostname}"
}

def runSmokeTests(url, user) {
    sh "./gradlew --full-stacktrace cfSmokeTest -Pcf.ccHost=${api} -Pcf.ccUser=${user} -Pcf.ccPassword=${password} -Pcf.org=${org} -Pcf.space=${space} -Pcf.domain=${domain} -Pcf.hostName=${hostname}"
}

def runAcceptanceTests(url, user) {
    sh "./gradlew --full-stacktrace cfAcceptanceTest -Pcf.ccHost=${api} -Pcf.ccUser=${user} -Pcf.ccPassword=${password} -Pcf.org=${org} -Pcf.space=${space} -Pcf.domain=${domain} -Pcf.hostName=${hostname}"

}

return this;
