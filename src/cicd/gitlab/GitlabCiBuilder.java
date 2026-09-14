package cicd.gitlab;

import cicd.PipelineBuilder;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class GitlabCiBuilder implements PipelineBuilder {

    private static final String TEST_STAGE = "test";
    private static final String DEPLOY_STAGE = "deploy";
    private static final String IMAGE_PREFIX = "maven:3.9-eclipse-temurin-";
    private static final int DEFAULT_JAVA_VERSION = 17;

    private String pipelineName;
    private String image = IMAGE_PREFIX + DEFAULT_JAVA_VERSION;
    private final Set<String> stages = new LinkedHashSet<>();
    private final List<GitlabJob> jobs = new ArrayList<>();

    @Override
    public PipelineBuilder named(String pipelineName) {
        this.pipelineName = pipelineName;
        return this;
    }

    @Override
    public PipelineBuilder checkout() {
        // GitLab Runner clones the repository before every job, so no explicit job is
        // needed.
        return this;
    }

    @Override
    public PipelineBuilder setupJava(int version) {
        this.image = IMAGE_PREFIX + version;
        return this;
    }

    @Override
    public PipelineBuilder runTests() {
        addJob("unit-tests", TEST_STAGE, "mvn test");
        return this;
    }

    @Override
    public PipelineBuilder deploy(String environment) {
        addJob("deploy-" + environment, DEPLOY_STAGE, "./deploy.sh " + environment);
        return this;
    }

    public GitlabCiConfig build() {
        if (pipelineName == null) {
            throw new IllegalStateException("call named(...) before build()");
        }
        if (jobs.isEmpty()) {
            throw new IllegalStateException("Pipeline must contain at least one job");
        }
        return new GitlabCiConfig(pipelineName, image, new ArrayList<>(stages), jobs);
    }

    private void addJob(String jobName, String stage, String command) {
        stages.add(stage);
        jobs.add(new GitlabJob(jobName, stage, List.of(command)));
    }
}