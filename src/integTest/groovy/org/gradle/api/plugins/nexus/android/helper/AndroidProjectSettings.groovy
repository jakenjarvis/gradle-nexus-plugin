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

/**
 * Settings of each project of Android project.
 *
 * @author jaken.jarvis@gmail.com
 */
public class AndroidProjectSettings {
    public static File DEFAULT_ANDROID_KEYSTORE_FILE = new File('src/integTest/groovy/org/gradle/api/plugins/nexus/android/gradle_nexus_plugin_android_test.keystore');
    public static String DEFAULT_PACKAGE_BASE_NAME = "org.gradle.api.plugins.nexus.android";

    public static AndroidProjectSettings clone(AndroidProjectSettings source) {
        AndroidProjectSettings target = new AndroidProjectSettings()
        target.androidGradlePluginVersion = source.androidGradlePluginVersion

        target.buildscriptDependenciesAndroidSdkManagerPluginVersion = source.buildscriptDependenciesAndroidSdkManagerPluginVersion

        target.androidCompileSdkVersion = source.androidCompileSdkVersion
        target.androidBuildToolsVersion = source.androidBuildToolsVersion

        target.androidDefaultConfigApplicationId = source.androidDefaultConfigApplicationId
        target.androidDefaultConfigMinSdkVersion = source.androidDefaultConfigMinSdkVersion
        target.androidDefaultConfigTargetSdkVersion = source.androidDefaultConfigTargetSdkVersion
        target.androidDefaultConfigVersionCode = source.androidDefaultConfigVersionCode
        target.androidDefaultConfigVersionName = source.androidDefaultConfigVersionName

        target.androidSigningConfigsStoreFile = source.androidSigningConfigsStoreFile
        target.androidSigningConfigsStorePassword = source.androidSigningConfigsStorePassword
        target.androidSigningConfigsKeyAlias = source.androidSigningConfigsKeyAlias
        target.androidSigningConfigsKeyPassword = source.androidSigningConfigsKeyPassword

        target.dependenciesAndroidVersion = source.dependenciesAndroidVersion

        target.projectMainSrcDirPath = source.projectMainSrcDirPath

        target.projectName = source.projectName
        target.projectDependencies = source.projectDependencies
        target.projectDir = source.projectDir
        target.projectMainSrcDir = source.projectMainSrcDir
        target.projectBuildGradleFile = source.projectBuildGradleFile
        target.projectAndroidManifestFile = source.projectAndroidManifestFile
        target.projectAndroidKeyStoreFile = source.projectAndroidKeyStoreFile

        target.outputsArtifactDir = source.outputsArtifactDir
        target
    }

    public AndroidProjectSettings() {
    }

    def androidGradlePluginVersion = AndroidGradlePluginVersion.Plugin012Gradle112

    // https://github.com/JakeWharton/sdk-manager-plugin
    def buildscriptDependenciesAndroidSdkManagerPluginVersion = "0.12.+"


    def androidCompileSdkVersion = 19
    def androidBuildToolsVersion = "19.1.0"

    def androidDefaultConfigApplicationId = ""
    def androidDefaultConfigMinSdkVersion = 8
    def androidDefaultConfigTargetSdkVersion = 19
    def androidDefaultConfigVersionCode = 1
    def androidDefaultConfigVersionName = "1.0.0"

    def androidSigningConfigsStoreFile = "gradle_nexus_plugin_android_test.keystore"
    def androidSigningConfigsStorePassword = "gradle_nexus_plugin_android_test"
    def androidSigningConfigsKeyAlias = "gradle_nexus_plugin_android_test"
    def androidSigningConfigsKeyPassword = "gradle_nexus_plugin_android_test"


    def dependenciesAndroidVersion = "4.1.1.4"

    //
    def projectMainSrcDirPath = "src/main"

    //
    String projectName = ""
    List<String> projectDependencies = []

    File projectDir = null
    File projectMainSrcDir = null

    File projectBuildGradleFile = null
    File projectAndroidManifestFile = null
    File projectAndroidKeyStoreFile = null

    File outputsArtifactDir = null
}
