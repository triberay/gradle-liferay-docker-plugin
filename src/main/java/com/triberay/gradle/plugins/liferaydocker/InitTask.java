package com.triberay.gradle.plugins.liferaydocker;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

import java.io.File;
import java.io.IOException;

public class InitTask extends DefaultTask {

    @TaskAction
    public void init() {

        LiferayDockerPluginExtension extension = (LiferayDockerPluginExtension) getProject().getExtensions().findByName("liferayDocker");

        File composeYml = getProject().file(extension.getDockerComposeFilePath());

        if (composeYml.exists()) {
            getLogger().quiet("nothing to be done, {} already exists", extension.getDockerComposeFilePath());
        } else {
            getLogger().quiet("generating {}", extension.getDockerComposeFilePath());
            try {
                composeYml.createNewFile();
            } catch (IOException e) {
                getLogger().error("error creating {}", extension.getDockerComposeFilePath());
                e.printStackTrace();
            }
        }

    }
}
