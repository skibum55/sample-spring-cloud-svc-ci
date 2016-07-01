def user = "malston"
// def registry = "localhost:5000"
def flow

node {
    stage 'clone project'
    echo "Cloning Project"
    git([url: "https://github.com/${user}/sample-spring-cloud-svc-ci.git", branch: 'Jenkinsfile'])
    flow = load 'ci/pipeline.groovy'
    flow.runCleanBuild(user)
}
// checkpoint "deploy"
// node("cd") {
//     flow.deploy(serviceName, registry)
//     flow.runPostDeploymentTests(serviceName, registry, "http://[IP]:8081")
// }
