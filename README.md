# CI/CD Pipeline Configuration Generator

Builder pattern in Java 17. Assignment #1, Software Design Patterns, AITU.

## What it is

The product is a CI/CD pipeline configuration. It is assembled step by step and
rendered into two structurally different representations: a GitHub Actions
workflow (`.github/workflows/ci.yml`) and a GitLab CI file (`.gitlab-ci.yml`).

## How to build each representation

Both builders accept the same construction steps and produce different products:

```java
GithubActionsBuilder githubBuilder = new GithubActionsBuilder();
new PipelineDirector().fullDeployPipeline(githubBuilder);
System.out.println(githubBuilder.build().toYaml());

GitlabCiBuilder gitlabBuilder = new GitlabCiBuilder();
new PipelineDirector().fullDeployPipeline(gitlabBuilder);
System.out.println(gitlabBuilder.build().toYaml());
```

Or directly through the fluent API:

```java
builder.named("CI").checkout().setupJava(17).runTests().deploy("prod");
```

## How to run

Requires JDK 17. No external dependencies.

```bash
javac -d bin src/cicd/*.java src/cicd/github/*.java src/cicd/gitlab/*.java
java -cp bin cicd.Main
```

In VS Code: open `src/cicd/Main.java` and press Run.