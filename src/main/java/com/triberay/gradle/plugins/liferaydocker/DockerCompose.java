package com.triberay.gradle.plugins.liferaydocker;

import org.gradle.api.DefaultTask;
import org.gradle.api.plugins.ExtensionAware;
import org.gradle.api.tasks.TaskAction;
import org.gradle.api.tasks.options.Option;

import java.util.*;

public class DockerCompose extends DefaultTask implements ExtensionAware {


    private List<String> dockerComposeArguments = new ArrayList<>();
    private Map<String, String> dockerComposeEnvironment = new HashMap<>();

    private String[] args;

    public DockerCompose() { }

    @Option(option = "args", description = "Set docker-compose arguments")
    public void setArgs(String[] args) {
        this.args = args;
    }

    @TaskAction
    public void dockerCompose() {

        DockerPluginExtension extension = loadExtension();

        getProject().exec(execSpec -> {
            execSpec.setExecutable(extension.getDockerComposeExecutable());
            execSpec.setArgs(dockerComposeArguments);
            execSpec.setEnvironment(dockerComposeEnvironment);
        });
    }

    private DockerPluginExtension loadExtension() {

        DockerPluginExtension extension = (DockerPluginExtension) getProject().getExtensions().findByName("docker");

        getLogger().quiet("plugin config:");
        getLogger().quiet("    project dir             = {}", getProject().getProjectDir().toString());
        getLogger().quiet("    bundleDirPath           = {}", extension.getBundleDirPath());
        getLogger().quiet("    dockerContainerPrefix   = {}", extension.getDockerContainerPrefix());
        getLogger().quiet("    dockerComposeFilePath   = {}", extension.getDockerComposeFilePath());
        getLogger().quiet("    dockerComposeExecutable = {}", extension.getDockerComposeExecutable());

        dockerComposeEnvironment.put("LIFERAY_BUNDLE_DIR", extension.getBundleDirPath());
        dockerComposeEnvironment.put("PROJECT_DIR", getProject().getProjectDir().toString());

        dockerComposeArguments.add("-p");
        dockerComposeArguments.add(extension.getDockerContainerPrefix());
        dockerComposeArguments.add("-f");
        dockerComposeArguments.add(extension.getDockerComposeFilePath());
        dockerComposeArguments.addAll(Arrays.asList(args));

        return extension;
    }
}
