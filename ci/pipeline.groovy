def runCleanBuild(project) {
    def maven = docker.image("maven:3.3.3-jdk-8")

    stage 'clone project'

    echo "Cloning Project"
    git ${project}

    stage 'Compile, Test and Package'
    maven.inside {
        sh './gradlew clean build'
    }
}

return this;
