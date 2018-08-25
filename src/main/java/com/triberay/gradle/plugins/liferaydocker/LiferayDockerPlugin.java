package com.triberay.gradle.plugins.liferaydocker;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

import java.util.Arrays;

public class LiferayDockerPlugin implements Plugin<Project> {

    private static final String TASKGROUP = "docker";

    public void apply(Project project) {
        /*project.getTasks().create("init", DockerCompose.class, (task) -> {
            task.setBundleDirPath("init");
            task.setGroup(TASKGROUP);
        });*/

        project.getTasks().create("startDockerEnv", DockerCompose.class, (task) -> {
            task.setGroup(TASKGROUP);
            task.setBundleDirPath("start");
            task.setArgs(Arrays.asList(new String[]{"up", "-d"}));
        });

        project.getTasks().create("stopDockerEnv", DockerCompose.class, (task) -> {
            task.setGroup(TASKGROUP);
            task.setBundleDirPath("stop");
            task.setArgs(Arrays.asList(new String[]{"down"}));
        });
    }
}