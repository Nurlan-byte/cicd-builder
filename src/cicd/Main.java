package cicd;

import java.util.List;

import cicd.github.GithubActionsBuilder;
import cicd.github.GithubActionsConfig;
import cicd.gitlab.GitlabJob;

public class Main {
    public static void main(String[] args) {
        GithubActionsBuilder githubBuilder = new GithubActionsBuilder();

        githubBuilder.named("CI").checkout().setupJava(17).runTests().deploy("prod");

        GithubActionsConfig config = githubBuilder.build();
        System.out.println(config.toYaml());

        GitlabJob tests = new GitlabJob("unit-tests", "test", List.of("mvn test"));
        GitlabJob deploy = new GitlabJob("deploy-prod", "deploy", List.of("./deploy.sh prod", "echo done"));
        System.out.println(tests.toYaml());
        System.out.println(deploy.toYaml());
    }
}