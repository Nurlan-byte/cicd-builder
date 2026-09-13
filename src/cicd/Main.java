package cicd;

import cicd.github.GithubActionsBuilder;
import cicd.github.GithubActionsConfig;
import cicd.gitlab.GitlabCiBuilder;
import cicd.gitlab.GitlabCiConfig;

public class Main {
    public static void main(String[] args) {
        GithubActionsBuilder githubBuilder = new GithubActionsBuilder();
        githubBuilder.named("CI").checkout().setupJava(17).runTests().deploy("prod");
        GithubActionsConfig config = githubBuilder.build();
        System.out.println(config.toYaml());

        GitlabCiBuilder gitlabBuilder = new GitlabCiBuilder();
        gitlabBuilder.named("CI").checkout().setupJava(17).runTests().deploy("prod");
        GitlabCiConfig gitlabConfig = gitlabBuilder.build();
        System.out.println(gitlabConfig.toYaml());
    }
}