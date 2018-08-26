package com.triberay.gradle.plugins.liferaydocker;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;
import org.gradle.api.tasks.options.Option;

import java.io.File;
import java.util.*;

public class DockerComposeTask extends DefaultTask {

    private List<String> dockerComposeArguments = new ArrayList<>();
    private Map<String, String> dockerComposeEnvironment = new HashMap<>();

    private String[] args;

    public DockerComposeTask() { }

    @Option(option = "args", description = "Set docker-compose arguments")
    public void setArgs(String[] args) {
        this.args = args;
    }

    @TaskAction
    public void dockerCompose() {

        LiferayDockerPluginExtension extension = loadExtension();

        File dockerComposeFile = getProject().file(extension.getDockerComposeFilePath());
        if (dockerComposeFile.exists()) {
            getProject().exec(execSpec -> {
                execSpec.setExecutable(extension.getDockerComposeExecutable());
                execSpec.setArgs(dockerComposeArguments);
                execSpec.setEnvironment(dockerComposeEnvironment);
            });
        }
    }

    private LiferayDockerPluginExtension loadExtension() {

        LiferayDockerPluginExtension extension = (LiferayDockerPluginExtension) getProject().getExtensions().findByName("liferayDocker");

        File dockerComposeFile = getProject().file(extension.getDockerComposeFilePath());
        if (!dockerComposeFile.exists()) {
            getLogger().quiet("'{}' does not exist!", extension.getDockerComposeFilePath());
            getLogger().quiet("either run the init taks to generate one, or configure the correct file path in your build.gradle file, for example:");
            getLogger().quiet("  liferayDocker {");
            getLogger().quiet("      dockerComposeFilePath = 'your-docker-compose.yaml'");
            getLogger().quiet("  }");
        }

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
