package cicd;

import java.util.List;

import cicd.github.GithubActionsBuilder;
import cicd.github.GithubActionsConfig;
import cicd.gitlab.GitlabCiConfig;
import cicd.gitlab.GitlabJob;

public class Main {
    public static void main(String[] args) {
        GithubActionsBuilder githubBuilder = new GithubActionsBuilder();

        githubBuilder.named("CI").checkout().setupJava(17).runTests().deploy("prod");

        GithubActionsConfig config = githubBuilder.build();
        System.out.println(config.toYaml());

        List<GitlabJob> jobs = List.of(
                new GitlabJob("unit-tests", "test", List.of("mvn test")),
                new GitlabJob("deploy-prod", "deploy", List.of("./deploy.sh prod")));

        GitlabCiConfig gitlabConfig = new GitlabCiConfig(
                "CI/CD",
                "maven:3.9-eclipse-temurin-17",
                List.of("test", "deploy"),
                jobs);

        System.out.println(gitlabConfig.toYaml());
    }
}