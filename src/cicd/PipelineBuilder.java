package cicd;

public interface PipelineBuilder {
    PipelineBuilder named(String pipelineName);

    PipelineBuilder checkout();

    PipelineBuilder setupJava(int version);

    PipelineBuilder runTests();

    PipelineBuilder deploy(String environment);
}
