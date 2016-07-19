def user = "pivotalservices"
def cfUser = "${cf_username}"
def cfPassword = "${cf_password}"
// def registry = "localhost:5000"
def flow

stage 'compile'
node {
	git([url: "https://github.com/${user}/sample-spring-cloud-svc-ci.git", branch: 'jenkins'])
	flow = load 'ci/pipeline.groovy'
	flow.build()
}

stage 'test'
node {
	git([url: "https://github.com/${user}/sample-spring-cloud-svc-ci.git", branch: 'jenkins'])
	flow = load 'ci/pipeline.groovy'
	flow.clean_test()
}

stage 'deploy-to-development'
node {
	git([url: "https://github.com/${user}/sample-spring-cloud-svc-ci.git", branch: 'jenkins'])
	flow = load 'ci/pipeline.groovy'
	flow.push('api.run.pez.pivotal.io', "${cfUser}", "${cfPassword}", 'pivot-bkunjummen', 'development', 'cfapps.pez.pivotal.io', 'sample-spring-cloud-svc-ci-dev')
}

stage 'run-tests-on-dev'
parallel(
	smokeTests: {
		node {
			git([url: "https://github.com/${user}/sample-spring-cloud-svc-ci.git", branch: 'jenkins'])
			flow = load 'ci/pipeline.groovy'
			flow.runSmokeTests('api.run.pez.pivotal.io', "${cfUser}", "${cfPassword}", 'pivot-bkunjummen', 'development', 'cfapps.pez.pivotal.io', 'sample-spring-cloud-svc-ci-dev')
		}
	},
	acceptanceTests: {
		node {
			git([url: "https://github.com/${user}/sample-spring-cloud-svc-ci.git", branch: 'jenkins'])
			flow = load 'ci/pipeline.groovy'
			flow.runAcceptanceTests('api.run.pez.pivotal.io', "${cfUser}", "${cfPassword}", 'pivot-bkunjummen', 'development', 'cfapps.pez.pivotal.io', 'sample-spring-cloud-svc-ci-dev')
		}
	}
)

stage 'deploy-to-test'
node {
	git([url: "https://github.com/${user}/sample-spring-cloud-svc-ci.git", branch: 'jenkins'])
	flow = load 'ci/pipeline.groovy'
	flow.pushIf('api.run.pez.pivotal.io', "${cfUser}", "${cfPassword}", 'pivot-bkunjummen', 'test', 'cfapps.pez.pivotal.io', 'sample-spring-cloud-svc-ci-test')
}

stage 'run-tests-on-test'
parallel(
	smokeTests: {
		node {
			git([url: "https://github.com/${user}/sample-spring-cloud-svc-ci.git", branch: 'jenkins'])
			flow = load 'ci/pipeline.groovy'
			flow.runSmokeTests('api.run.pez.pivotal.io', "${cfUser}", "${cfPassword}", 'pivot-bkunjummen', 'test', 'cfapps.pez.pivotal.io', 'sample-spring-cloud-svc-ci-test')
		}
	},
	acceptanceTests: {
		node {
			git([url: "https://github.com/${user}/sample-spring-cloud-svc-ci.git", branch: 'jenkins'])
			flow = load 'ci/pipeline.groovy'
			flow.runAcceptanceTests('api.run.pez.pivotal.io', "${cfUser}", "${cfPassword}", 'pivot-bkunjummen', 'test', 'cfapps.pez.pivotal.io', 'sample-spring-cloud-svc-ci-test')
		}
	}
)

stage 'deploy-to-prod'
node {
	git([url: "https://github.com/${user}/sample-spring-cloud-svc-ci.git", branch: 'jenkins'])
	flow = load 'ci/pipeline.groovy'
	flow.pushIf('api.run.pez.pivotal.io', "${cfUser}", "${cfPassword}", 'pivot-bkunjummen', 'prod', 'cfapps.pez.pivotal.io', 'sample-spring-cloud-svc-ci-prod')
}
