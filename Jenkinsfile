def user = "malston"
def cfUser = "${cf_username}"
def cfPassword = "${cf_password}"
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
checkpoint "deploy to cf"
parallel(
    deployToDev: {
        node("cd") {
		    flow = load 'ci/pipeline.groovy'
		    flow.push('dev', 'api.run.pez.pivotal.io', "${cfUser}", "${cfPassword}", 'pivot-bkunjummen', 'development', 'cfapps.pez.pivotal.io', 'sample-spring-cloud-svc-ci-dev')
        }
    },
    deployToTest: {
        node("cd") {
		    flow = load 'ci/pipeline.groovy'
		    flow.push('test', 'api.run.pez.pivotal.io', "${cfUser}", "${cfPassword}", 'pivot-bkunjummen', 'test', 'cfapps.pez.pivotal.io', 'sample-spring-cloud-svc-ci-test')
        }
    }
)
checkpoint "run acceptance & smoke tests"
parallel(
    smokeTests: {
        node {
			stage 'run smoke tests'
            checkout scm
            mvn "test -P smoke -Durl=http://sample-spring-cloud-svc-ci-dev.cfapps.pez.pivotal.io/message"
        }
    },
    acceptanceTests: {
        node {
			stage 'run acceptance tests'
            checkout scm
            mvn "test -P acceptance -Durl=http://sample-spring-cloud-svc-ci-test.cfapps.pez.pivotal.io/message"
        }
    }
)
checkpoint "deploy to prod"
node("cd") {
    flow = load 'ci/pipeline.groovy'
    flow.push_and_verify('prod', 'api.run.pez.pivotal.io', "${cfUser}", "${cfPassword}", 'pivot-bkunjummen', 'prod', 'cfapps.pez.pivotal.io', 'sample-spring-cloud-svc-ci-prod')
}
