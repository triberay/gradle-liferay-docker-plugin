package com.triberay.gradle.plugins.liferaydocker;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class LiferayDockerPlugin implements Plugin<Project> {

    private static final String TASKGROUP = "docker";

    public void apply(Project project) {

        project.getExtensions().add("docker", new DockerPluginExtension());

        /*project.getTasks().create("init", DockerCompose.class, (task) -> {
            task.setBundleDirPath("init");
            task.setGroup(TASKGROUP);
        });*/

        project.getTasks().create("startDockerEnv", DockerCompose.class, (task) -> {
            task.setGroup(TASKGROUP);
            task.setArgs(new String[]{"up", "-d"});
        });

        project.getTasks().create("stopDockerEnv", DockerCompose.class, (task) -> {
            task.setGroup(TASKGROUP);
            task.setArgs(new String[]{"down"});
        });
    }
}