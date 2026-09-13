package cicd.gitlab;

import java.util.ArrayList;
import java.util.List;

public class GitlabCiConfig {

    private final String pipelineName;
    private final String image;
    private final List<String> stages;
    private final List<GitlabJob> jobs;

    public GitlabCiConfig(String pipelineName, String image, List<String> stages, List<GitlabJob> jobs) {
        this.pipelineName = pipelineName;
        this.image = image;
        this.stages = new ArrayList<>(stages);
        this.jobs = new ArrayList<>(jobs);
    }

    public String toYaml() {
        StringBuilder yaml = new StringBuilder();

        yaml.append("# ").append(pipelineName).append("\n\n");
        yaml.append("image: ").append(image).append("\n\n");
        yaml.append("stages:\n");
        for (String stage : stages) {
            yaml.append("  - ").append(stage).append("\n");
        }
        for (GitlabJob job : jobs) {
            yaml.append("\n").append(job.toYaml());
        }

        return yaml.toString();
    }
}