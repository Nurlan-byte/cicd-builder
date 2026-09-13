package cicd;

import cicd.github.GithubActionsConfig;
import cicd.github.GithubStep;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Map<String, String> javaParameters = new LinkedHashMap<>();
        javaParameters.put("distribution", "temurin");
        javaParameters.put("java-version", "17");

        List<GithubStep> steps = new ArrayList<>();
        steps.add(GithubStep.usingAction("Checkout code", "actions/checkout@v4", new LinkedHashMap<>()));
        steps.add(GithubStep.usingAction("Set up Java", "actions/setup-java@v4", javaParameters));
        steps.add(GithubStep.runningCommand("Run tests", "mvn test"));

        GithubActionsConfig config = new GithubActionsConfig("CI", "main", "build", "ubuntu-latest", steps);

        System.out.println(config.toYaml());
    }
}