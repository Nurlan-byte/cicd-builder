package cicd.gitlab;

import java.util.ArrayList;
import java.util.List;

public class GitlabJob {

    private final String name;
    private final String stage;
    private final List<String> commands;

    public GitlabJob(String name, String stage, List<String> commands) {
        this.name = name;
        this.stage = stage;
        this.commands = new ArrayList<>(commands);
    }

    public String toYaml() {
        StringBuilder yaml = new StringBuilder();
        yaml.append(name).append(":\n");
        yaml.append("  stage: ").append(stage).append("\n");
        yaml.append("  script:\n");
        for (String command : commands) {
            yaml.append("    - ").append(command).append("\n");
        }
        return yaml.toString();
    }
}