package com.triberay.gradle.plugins.liferaydocker;

public class DockerPluginExtension {

    private String bundleDirPath = "./bundles";

    private String dockerContainerPrefix = "triberay";

    private String dockerComposeFilePath = "./docker-compose.yaml";

    private String dockerComposeExecutable = "docker-compose";

    public DockerPluginExtension() {
    }

    public String getBundleDirPath() {
        return bundleDirPath;
    }

    public void setBundleDirPath(String bundleDirPath) {
        this.bundleDirPath = bundleDirPath;
    }

    public String getDockerContainerPrefix() {
        return dockerContainerPrefix;
    }

    public void setDockerContainerPrefix(String dockerContainerPrefix) {
        this.dockerContainerPrefix = dockerContainerPrefix;
    }

    public String getDockerComposeFilePath() {
        return dockerComposeFilePath;
    }

    public void setDockerComposeFilePath(String dockerComposeFilePath) {
        this.dockerComposeFilePath = dockerComposeFilePath;
    }

    public String getDockerComposeExecutable() {
        return dockerComposeExecutable;
    }

    public void setDockerComposeExecutable(String dockerComposeExecutable) {
        this.dockerComposeExecutable = dockerComposeExecutable;
    }
}
