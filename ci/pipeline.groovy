def runCleanBuild(user) {
    def maven = docker.image("maven:3.3.3-jdk-8")
    stage 'Compile, Test and Package'
    maven.inside {
        sh './gradlew clean build'
    }
}

return this;
