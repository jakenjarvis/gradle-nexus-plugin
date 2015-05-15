/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.gradle.api.plugins.nexus.android

import org.gradle.api.plugins.nexus.AbstractIntegrationTest
import org.gradle.api.plugins.nexus.android.helper.AndroidBuildGradleBuilderInterface
import org.gradle.api.plugins.nexus.android.helper.AndroidProjectSettings

/**
 * Abstract integregration test using Gradle's tooling API with android sdk.
 *
 * @author jaken.jarvis@gmail.com
 */
abstract class AbstractAndroidIntegrationTest extends AbstractIntegrationTest {
    protected AndroidProjectSettings rootSettings
    protected File settingsFile

    protected Map<String, AndroidProjectSettings> subprojects = [:]

    protected void onSetup() {
        rootSettings = createRootAndroidProjectSettings()
        AndroidBuildGradleBuilderInterface builder = rootSettings.androidGradlePluginVersion.getBuilder()

        buildFile = createNewFile(integTestDir, 'build.gradle')
        buildFile << builder.getRootBuildGradleString(rootSettings)

        settingsFile = createNewFile(integTestDir, 'settings.gradle')
    }

    protected AndroidProjectSettings createRootAndroidProjectSettings() {
        new AndroidProjectSettings()
    }

    protected void AddSubProjects(AndroidProjectSettings settings) {
        subprojects += [(settings.projectName) : settings]
    }

    protected AndroidProjectSettings createAndroidProjectSettingsForAndroidApplication(String projectName, List<String> dependencies) {
        AndroidProjectSettings settings = AndroidProjectSettings.clone(rootSettings)

        settings.androidDefaultConfigApplicationId = "${AndroidProjectSettings.DEFAULT_PACKAGE_BASE_NAME}.${projectName}"

        settings.projectName = projectName
        settings.projectDependencies = dependencies

        settings.projectDir = createNewDir(integTestDir, projectName)
        settings.projectMainSrcDir = createNewDir(settings.projectDir, settings.projectMainSrcDirPath)

        settings.projectBuildGradleFile = createBuildGradleForAndroidApplication(settings)
        settings.projectAndroidManifestFile = createNewEmptyAndroidManifestFile(settings)
        settings.projectAndroidKeyStoreFile = createKeyStoreFile(settings)

        settings.outputsArtifactDir = new File(integTestDir, "${projectName}/${settings.androidGradlePluginVersion.getOutputsApkDirPath()}")

        settingsFile << "include '$projectName'\n"
        settings
    }

    protected File createBuildGradleForAndroidApplication(AndroidProjectSettings settings) {
        AndroidBuildGradleBuilderInterface builder = settings.androidGradlePluginVersion.getBuilder()
        File file = createNewFile(settings.projectDir, 'build.gradle')
        file << builder.getAndroidApplicationProjectString(settings)
        file
    }

    protected AndroidProjectSettings createAndroidProjectSettingsForAndroidLibrary(String projectName, List<String> dependencies) {
        AndroidProjectSettings settings = AndroidProjectSettings.clone(rootSettings)

        settings.androidDefaultConfigApplicationId = "${AndroidProjectSettings.DEFAULT_PACKAGE_BASE_NAME}.${projectName}"

        settings.projectName = projectName
        settings.projectDependencies = dependencies

        settings.projectDir = createNewDir(integTestDir, projectName)
        settings.projectMainSrcDir = createNewDir(settings.projectDir, settings.projectMainSrcDirPath)

        settings.projectBuildGradleFile = createBuildGradleForAndroidLibrary(settings)
        settings.projectAndroidManifestFile = createNewEmptyAndroidManifestFile(settings)
        settings.projectAndroidKeyStoreFile = null //createKeyStoreFile(settings)

        settings.outputsArtifactDir = new File(integTestDir, "${projectName}/${settings.androidGradlePluginVersion.getOutputsAarDirPath()}")

        settingsFile << "include '$projectName'\n"
        settings
    }

    protected File createBuildGradleForAndroidLibrary(AndroidProjectSettings settings) {
        AndroidBuildGradleBuilderInterface builder = settings.androidGradlePluginVersion.getBuilder()
        File file = createNewFile(settings.projectDir, 'build.gradle')
        file << builder.getAndroidLibraryProjectString(settings)
        file
    }

    protected AndroidProjectSettings createAndroidProjectSettingsForJavaLibrary(String projectName, List<String> dependencies) {
        // TODO:
        AndroidProjectSettings settings = new AndroidProjectSettings()
        //settings.outputsArtifactDir = new File(integTestDir, "${projectName}/build/libs")
        settings
    }

    protected File createBuildGradleForJavaLibrary(AndroidProjectSettings settings) {
        AndroidBuildGradleBuilderInterface builder = settings.androidGradlePluginVersion.getBuilder()
        File file = createNewFile(settings.projectDir, 'build.gradle')
        file << builder.getJavaLibraryProjectString(settings)
        file
    }

    protected File createKeyStoreFile(AndroidProjectSettings settings) {
        copyFile(AndroidProjectSettings.DEFAULT_ANDROID_KEYSTORE_FILE, new File(settings.projectDir, settings.getAndroidSigningConfigsStoreFile()))
    }

    protected File createNewEmptyAndroidManifestFile(AndroidProjectSettings settings) {
        AndroidBuildGradleBuilderInterface builder = settings.androidGradlePluginVersion.getBuilder()
        File file = createNewFile(settings.projectMainSrcDir, 'AndroidManifest.xml')
        file << builder.getEmptyAndroidManifestString(settings)
        file
    }

}
