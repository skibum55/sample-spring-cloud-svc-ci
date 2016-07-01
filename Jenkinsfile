def user = "malston"
// def registry = "localhost:5000"
def flow

node("cd") {
    stage 'clone project'
    echo "Cloning Project"
    git([url: "https://github.com/${user}/sample-spring-cloud-svc-ci.git", branch: 'Jenkinsfile'])
    flow = load 'ci/pipeline.groovy'
    flow.build()
}
checkpoint "test"
node("cd") {
    flow = load 'ci/pipeline.groovy'
    flow.clean_test()
}
checkpoint "deploy to dev"
node("cd") {
    flow = load 'ci/pipeline.groovy'
    flow.push_to_dev('api.run.pez.pivotal.io', 'bkunjummen+jenkins@pivotal.io', 'jenkins', 'pivot-bkunjummen', 'development', 'cfapps.pez.pivotal.io', 'sample-spring-cloud-svc-ci-dev')
}
checkpoint "deploy to test"
node("cd") {
    flow = load 'ci/pipeline.groovy'
    flow.push_to_dev('api.run.pez.pivotal.io', 'bkunjummen+jenkins@pivotal.io', 'jenkins', 'pivot-bkunjummen', 'test', 'cfapps.pez.pivotal.io', 'sample-spring-cloud-svc-ci-test')
}
checkpoint "deploy to prod"
node("cd") {
    flow = load 'ci/pipeline.groovy'
    flow.push_to_dev('api.run.pez.pivotal.io', 'bkunjummen+jenkins@pivotal.io', 'jenkins', 'pivot-bkunjummen', 'prod', 'cfapps.pez.pivotal.io', 'sample-spring-cloud-svc-ci-prod')
}
