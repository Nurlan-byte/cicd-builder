package cicd;

public class PipelineDirector {

    private static final int JAVA_VERSION = 17;

    public void minimalTestPipeline(PipelineBuilder builder) {
        builder.named("CI")
                .checkout()
                .setupJava(JAVA_VERSION)
                .runTests();
    }

    public void fullDeployPipeline(PipelineBuilder builder) {
        builder.named("CI/CD")
                .checkout()
                .setupJava(JAVA_VERSION)
                .runTests()
                .deploy("prod");
    }
}