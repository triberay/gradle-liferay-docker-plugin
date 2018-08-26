package com.triberay.gradle.plugins.liferaydocker;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class LiferayDockerPlugin implements Plugin<Project> {

    private static final String TASKGROUP = "docker";

    public void apply(Project project) {

        project.getExtensions().add("liferayDocker", new LiferayDockerPluginExtension());

        project.getTasks().create("init", InitTask.class, (task) -> {
            task.setGroup(TASKGROUP);
        });

        project.getTasks().create("startDockerEnv", DockerComposeTask.class, (task) -> {
            task.setGroup(TASKGROUP);
            task.setArgs(new String[]{"up", "-d"});
        });

        project.getTasks().create("stopDockerEnv", DockerComposeTask.class, (task) -> {
            task.setGroup(TASKGROUP);
            task.setArgs(new String[]{"down"});
        });
    }
}