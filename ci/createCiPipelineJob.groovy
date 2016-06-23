pipelineJob('Sample CD Pipeline Job') {
    definition {
        cps {
            script(readFileFromWorkspace('pipeline.groovy'))
            sandbox()
        }
    }
}