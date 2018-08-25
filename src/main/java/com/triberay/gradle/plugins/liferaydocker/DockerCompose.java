package com.triberay.gradle.plugins.liferaydocker;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;
import org.gradle.api.tasks.options.Option;

import java.util.*;

public class DockerCompose extends DefaultTask {

    private String bundleDirPath;

    @Option(option = "bundleDirPath", description = "Configures directory where bundles are deployed")
    public void setBundleDirPath(String bundleDirPath) {
        this.bundleDirPath = bundleDirPath;
    }

    @Option(option = "args", description = "Set docker-compose arguments")
    public void setArgs(List<String> args) {
        this.dockerComposeArguments.addAll(args);
    }

    private List<String> dockerComposeArguments = new ArrayList<>();
    private Map<String, String> dockerComposeEnvironment = new HashMap<>();

    public DockerCompose() {
        dockerComposeEnvironment.put("LIFERAY_BUNDLE_DIR", bundleDirPath);
        dockerComposeEnvironment.put("PROJECT_DIR", getProject().getProjectDir().toString());

        dockerComposeArguments.add("-p");
        dockerComposeArguments.add("triberay");
        dockerComposeArguments.add("-f");
        dockerComposeArguments.add("docker-compose.yaml");
    }

    @TaskAction
    public void dockerCompose() {

        getLogger().quiet("bundleDirPath = {}", bundleDirPath);

        getProject().exec(execSpec -> {
            execSpec.setExecutable("docker-compose");
            execSpec.setArgs(dockerComposeArguments);
            execSpec.setEnvironment(dockerComposeEnvironment);
        });
    }
}
