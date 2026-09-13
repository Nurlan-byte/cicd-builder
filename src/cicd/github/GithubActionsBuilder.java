package cicd.github;

import cicd.PipelineBuilder;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class GithubActionsBuilder implements PipelineBuilder {

    private static final String DEFAULT_JOB_NAME = "build";
    private static final String DEFAULT_RUNNER = "ubuntu-latest";
    private static final String DEFAULT_BRANCH = "main";

    private String workflowName;
    private final List<GithubStep> steps = new ArrayList<>();

    @Override
    public PipelineBuilder named(String pipelineName) {
        this.workflowName = pipelineName;
        return this;
    }

    @Override
    public PipelineBuilder checkout() {
        steps.add(GithubStep.usingAction("Checkout code", "actions/checkout@v4", new LinkedHashMap<>()));
        return this;
    }

    @Override
    public PipelineBuilder setupJava(int version) {
        Map<String, String> parameters = new LinkedHashMap<>();
        parameters.put("distribution", "temurin");
        parameters.put("java-version", quoted(version));
        steps.add(GithubStep.usingAction("Set up Java", "actions/setup-java@v4", parameters));
        return this;
    }

    @Override
    public PipelineBuilder runTests() {
        steps.add(GithubStep.runningCommand("Run tests", "mvn test"));
        return this;
    }

    @Override
    public PipelineBuilder deploy(String environment) {
        steps.add(GithubStep.runningCommand("Deploy", "./deploy.sh " + environment));
        return this;
    }

    public GithubActionsConfig build() {
        if (workflowName == null) {
            throw new IllegalStateException("call named(...) before build()");
        }
        if (steps.isEmpty()) {
            throw new IllegalStateException("Pipeline must contain at least one step");
        }
        return new GithubActionsConfig(workflowName, DEFAULT_BRANCH, DEFAULT_JOB_NAME, DEFAULT_RUNNER, steps);
    }

    private String quoted(int value) {
        return "'" + value + "'";
    }
}