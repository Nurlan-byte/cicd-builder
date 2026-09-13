package cicd;

import cicd.github.GithubStep;
import java.util.LinkedHashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Map<String, String> javaParameters = new LinkedHashMap<>();
        javaParameters.put("distribution", "temurin");
        javaParameters.put("java-version", "17");

        GithubStep setupJava = GithubStep.usingAction("Set up Java", "actions/setup-java@v4", javaParameters);
        GithubStep runTests = GithubStep.runningCommand("Run tests", "mvn test");

        System.out.print(setupJava.toYaml("      "));
        System.out.print(runTests.toYaml("      "));
    }
}