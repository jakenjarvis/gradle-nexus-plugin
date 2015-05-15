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
package org.gradle.api.plugins.nexus.android.helper

import org.gradle.api.plugins.nexus.android.helper.builder.AndroidBuildGradleBuilderFeatureApplicationId

/**
 * Represent each version of the Android Gradle Plugin.
 *
 * @author jaken.jarvis@gmail.com
 */
public enum AndroidGradlePluginVersion {
    // http://tools.android.com/tech-docs/new-build-system
    // http://tools.android.com/tech-docs/new-build-system/version-compatibility
    Plugin012Gradle112("0.12+", "1.12", new AndroidBuildGradleBuilderFeatureApplicationId(), "build/outputs/apk", "build/outputs/aar"),
    Plugin011Gradle112("0.11+", "1.12", new AndroidBuildGradleBuilderFeatureApplicationId(), "build/outputs/apk", "build/outputs/aar"),
    Plugin010Gradle112("0.10+", "1.12", null, "", ""),
    Plugin009Gradle111("0.9+", "1.11", null, "", ""),
    Plugin008Gradle110("0.8+", "1.10", null, "", ""),
    Plugin007Gradle109("0.7+", "1.9", null, "", "");

    private AndroidGradlePluginVersion(String pluginVersion, String gradleVersion, AndroidBuildGradleBuilderInterface androidBuildGradleBuilder, String outputsApkDirPath, String outputsAarDirPath) {
        this.pluginVersion = pluginVersion
        this.gradleVersion = gradleVersion
        this.androidBuildGradleBuilder = androidBuildGradleBuilder
        this.outputsApkDirPath = outputsApkDirPath
        this.outputsAarDirPath = outputsAarDirPath
    }

    private final String pluginVersion
    private final String gradleVersion
    private final AndroidBuildGradleBuilderInterface androidBuildGradleBuilder
    private final String outputsApkDirPath
    private final String outputsAarDirPath

    String getPluginVersion() {
        return pluginVersion
    }

    String getGradleVersion() {
        return gradleVersion
    }

    AndroidBuildGradleBuilderInterface getBuilder() {
        return androidBuildGradleBuilder
    }

    String getOutputsApkDirPath() {
        return outputsApkDirPath
    }

    String getOutputsAarDirPath() {
        return outputsAarDirPath
    }
}
