def serviceName = "sample-spring-cloud-svc-ci"
// def registry = "localhost:5000"
def flow

node("cd") {
    git "https://github.com/malston/${serviceName}.git"
    flow = load script(readFileFromWorkspace('ci/pipeline.groovy'))
    flow.runCleanBuild(git)
}
// checkpoint "deploy"
// node("cd") {
//     flow.deploy(serviceName, registry)
//     flow.runPostDeploymentTests(serviceName, registry, "http://[IP]:8081")
// }
