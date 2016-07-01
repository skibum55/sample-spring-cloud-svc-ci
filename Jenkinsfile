def serviceName = "sample-spring-cloud-svc-ci"
// def registry = "localhost:5000"
def flow

node {
    flow = load 'ci/pipeline.groovy'
    flow.runCleanBuild("https://github.com/malston/${serviceName}.git")
}
// checkpoint "deploy"
// node("cd") {
//     flow.deploy(serviceName, registry)
//     flow.runPostDeploymentTests(serviceName, registry, "http://[IP]:8081")
// }
