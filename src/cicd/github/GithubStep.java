package cicd.github;

import java.util.LinkedHashMap;
import java.util.Map;

public class GithubStep {

    private final String name;
    private final String action;
    private final Map<String, String> parameters;
    private final String command;

    private GithubStep(String name, String action, Map<String, String> parameters, String command) {
        this.name = name;
        this.action = action;
        this.parameters = parameters;
        this.command = command;
    }

    public static GithubStep usingAction(String name, String action, Map<String, String> parameters) {
        return new GithubStep(name, action, parameters, null);
    }

    public static GithubStep runningCommand(String name, String command) {
        return new GithubStep(name, null, new LinkedHashMap<>(), command);
    }

    public String toYaml(String indent) {
        StringBuilder yaml = new StringBuilder();
        yaml.append(indent).append("- name: ").append(name).append("\n");

        if (action != null) {
            yaml.append(indent).append("  uses: ").append(action).append("\n");
            if (!parameters.isEmpty()) {
                yaml.append(indent).append("  with:\n");
                for (Map.Entry<String, String> parametr : parameters.entrySet()) {
                    yaml.append(indent).append("      ").append(parametr.getKey()).append(": ")
                            .append(parametr.getValue()).append("\n");
                }
            }
        } else {
            yaml.append(indent).append("  run: ").append(command).append("\n");
        }

        return yaml.toString();
    }
}
