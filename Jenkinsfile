def user = "pivotalservices"
def cfUser = "${cf_username}"
def cfPassword = "${cf_password}"
// def registry = "localhost:5000"
def flow

node {
	git([url: "https://github.com/${user}/sample-spring-cloud-svc-ci.git", branch: 'jenkins'])
	flow = load 'ci/pipeline.groovy'
	flow.build()
}
checkpoint "test"
node {
	git([url: "https://github.com/${user}/sample-spring-cloud-svc-ci.git", branch: 'jenkins'])
	flow = load 'ci/pipeline.groovy'
	flow.clean_test()
}
checkpoint "deploy to development"
node {
	git([url: "https://github.com/${user}/sample-spring-cloud-svc-ci.git", branch: 'jenkins'])
	flow = load 'ci/pipeline.groovy'
	flow.push('api.run.pez.pivotal.io', "${cfUser}", "${cfPassword}", 'pivot-bkunjummen', 'development', 'cfapps.pez.pivotal.io', 'sample-spring-cloud-svc-ci-dev')
}
checkpoint "run tests on dev"
parallel(
	smokeTests: {
		node {
			git([url: "https://github.com/${user}/sample-spring-cloud-svc-ci.git", branch: 'jenkins'])
			flow = load 'ci/pipeline.groovy'
			flow.runSmokeTests('https://sample-spring-cloud-svc-ci-dev.cfapps.pez.pivotal.io/message', user)
		}
	},
	acceptanceTests: {
		node {
			git([url: "https://github.com/${user}/sample-spring-cloud-svc-ci.git", branch: 'jenkins'])
			flow = load 'ci/pipeline.groovy'
			flow.runAcceptanceTests('https://sample-spring-cloud-svc-ci-dev.cfapps.pez.pivotal.io/message', user)
		}
	}
)
checkpoint "deploy to test"
node {
	git([url: "https://github.com/${user}/sample-spring-cloud-svc-ci.git", branch: 'jenkins'])
	flow = load 'ci/pipeline.groovy'
	flow.pushIf('api.run.pez.pivotal.io', "${cfUser}", "${cfPassword}", 'pivot-bkunjummen', 'test', 'cfapps.pez.pivotal.io', 'sample-spring-cloud-svc-ci-test')
}
checkpoint "run tests on test"
parallel(
	smokeTests: {
		node {
			git([url: "https://github.com/${user}/sample-spring-cloud-svc-ci.git", branch: 'jenkins'])
			flow = load 'ci/pipeline.groovy'
			flow.runSmokeTests('https://sample-spring-cloud-svc-ci-dev.cfapps.pez.pivotal.io/message', user)
		}
	},
	acceptanceTests: {
		node {
			git([url: "https://github.com/${user}/sample-spring-cloud-svc-ci.git", branch: 'jenkins'])
			flow = load 'ci/pipeline.groovy'
			flow.runAcceptanceTests('https://sample-spring-cloud-svc-ci-dev.cfapps.pez.pivotal.io/message', user)
		}
	}
)
checkpoint "deploy to prod"
node {
	git([url: "https://github.com/${user}/sample-spring-cloud-svc-ci.git", branch: 'jenkins'])
	flow = load 'ci/pipeline.groovy'
	flow.pushIf('api.run.pez.pivotal.io', "${cfUser}", "${cfPassword}", 'pivot-bkunjummen', 'prod', 'cfapps.pez.pivotal.io', 'sample-spring-cloud-svc-ci-prod')
}
