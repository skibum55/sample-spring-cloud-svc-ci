echo "Starting workflow"

node {
    stage 'clone project'

    echo "Cloning Project"
    git 'https://github.com/bijukunjummen/sample-spring-cloud-svc-ci'

    stage 'Compile Code'
    echo "Building the Project with Maven Wrapper"
    env.JAVA_HOME="${tool 'jdk-8'}"
    env.PATH="${env.JAVA_HOME}/bin:${env.PATH}"
    sh './mvnw compile'

    stage 'Test and Package'
    sh './mvnw package'
}
