package cicd.github;

import java.util.ArrayList;
import java.util.List;

public class GithubActionsConfig {

    private static final String STEP_INDENT = "      ";
    private final String workflowName;
    private final String triggerBranch;
    private final String jobName;
    private final String runner;
    private final List<GithubStep> steps;

    public GithubActionsConfig(String workflowName, String triggerBranch, String jobName, String runner,
            List<GithubStep> steps) {
        this.workflowName = workflowName;
        this.triggerBranch = triggerBranch;
        this.jobName = jobName;
        this.runner = runner;
        this.steps = new ArrayList<>(steps);
    }

    public String toYaml() {
        StringBuilder yaml = new StringBuilder();

        yaml.append("name: ").append(workflowName).append("\n");
        yaml.append("on:\n");
        yaml.append("  push:\n");
        yaml.append("    branches: [ ").append(triggerBranch).append(" ]\n");
        yaml.append("\n");
        yaml.append("jobs:\n");
        yaml.append("  ").append(jobName).append(":\n");
        yaml.append("    runs-on: ").append(runner).append("\n");
        yaml.append("    steps:\n");

        for (GithubStep step : steps) {
            yaml.append(step.toYaml(STEP_INDENT));
        }

        return yaml.toString();
    }
}
