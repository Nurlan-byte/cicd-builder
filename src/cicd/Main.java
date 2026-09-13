package cicd;

import cicd.github.GithubActionsBuilder;
import cicd.gitlab.GitlabCiBuilder;

public class Main {

    public static void main(String[] args) {
        PipelineDirector director = new PipelineDirector();

        GithubActionsBuilder githubBuilder = new GithubActionsBuilder();
        GitlabCiBuilder gitlabBuilder = new GitlabCiBuilder();

        director.fullDeployPipeline(githubBuilder);
        director.fullDeployPipeline(gitlabBuilder);

        printFile(".github/workflows/ci.yml", githubBuilder.build().toYaml());
        printFile(".gitlab-ci.yml", gitlabBuilder.build().toYaml());
    }

    private static void printFile(String fileName, String content) {
        System.out.println(fileName);
        System.out.println(content);
    }
}