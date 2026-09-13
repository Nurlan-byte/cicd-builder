package cicd;

import cicd.github.GithubActionsBuilder;
import cicd.github.GithubActionsConfig;

public class Main {
    public static void main(String[] args) {
        GithubActionsBuilder githubBuilder = new GithubActionsBuilder();

        githubBuilder.named("CI").checkout().setupJava(17).runTests().deploy("prod");

        GithubActionsConfig config = githubBuilder.build();
        System.out.println(config.toYaml());
    }
}