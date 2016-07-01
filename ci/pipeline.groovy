def runCleanBuild(project) {
    stage 'clone project'

    echo "Cloning Project"
    git ${project}

    stage 'Compile, Test and Package'
    // Test modification..
    sh './gradlew clean build'
}

return this;
