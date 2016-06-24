echo "Starting workflow"

node {
    stage 'clone project'

    echo "Cloning Project"
    git 'https://github.com/bijukunjummen/sample-spring-cloud-svc-ci'

    stage 'Compile, Test and Package'
    sh './gradlew clean build'
}
