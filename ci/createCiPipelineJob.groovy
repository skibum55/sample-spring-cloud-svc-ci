pipelineJob('Sample CD Pipeline Job') {
    definition {
        cps {
            script(readFileFromWorkspace('ci/pipeline.groovy'))
            sandbox()
        }
    }
}