echo "Starting workflow"

def runCleanBuild(project)
node {
    stage 'clone project'

    echo "Cloning Project"
    git ${project}

    stage 'Compile, Test and Package'
    // Test modification..
    sh './gradlew clean build'
}
