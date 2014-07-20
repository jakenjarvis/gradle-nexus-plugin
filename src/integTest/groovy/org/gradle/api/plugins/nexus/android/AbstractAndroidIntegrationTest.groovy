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

/**
 * Abstract integregration test using Gradle's tooling API with android sdk.
 *
 * @author jaken.jarvis@gmail.com
 */
abstract class AbstractAndroidIntegrationTest extends AbstractIntegrationTest {
    def buildTargetGradleVersion = "1.12"

    // https://github.com/JakeWharton/sdk-manager-plugin
    def buildscriptDependenciesAndroidSdkManagerPluginVersion = "0.12.+"

    // http://tools.android.com/tech-docs/new-build-system/version-compatibility
    def buildscriptDependenciesAndroidGradlePluginVersion = "0.12.+"

    def dependenciesAndroidVersion = "4.1.1.4"

    def androidCompileSdkVersion = 19
    def androidBuildToolsVersion = "19.1.0"

    def androidDefaultConfigApplicationId = "org.gradle.api.plugins.nexus.android"
    def androidDefaultConfigMinSdkVersion = 8
    def androidDefaultConfigTargetSdkVersion = 19
    def androidDefaultConfigVersionCode = 1
    def androidDefaultConfigVersionName = "1.0.0"

    def defaultAndroidKeyStoreFile = new File('src/integTest/groovy/org/gradle/api/plugins/nexus/android/gradle_nexus_plugin_android_test.keystore')

    def androidStoreFile = "gradle_nexus_plugin_android_test.keystore"
    def androidStorePassword = "gradle_nexus_plugin_android_test"
    def androidKeyAlias = "gradle_nexus_plugin_android_test"
    def androidKeyPassword = "gradle_nexus_plugin_android_test"

    protected void writeDefaultBuildFile(File buildfile) {
        buildfile << """
buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath files('../classes/main')
        classpath 'com.jakewharton.sdkmanager:gradle-plugin:${buildscriptDependenciesAndroidSdkManagerPluginVersion}'
        classpath 'com.android.tools.build:gradle:${buildscriptDependenciesAndroidGradlePluginVersion}'
    }
}
apply plugin: 'android-sdk-manager'
allprojects {
    repositories {
        mavenCentral()
    }
}

"""
    }

    protected File createNewEmptyAndroidManifestFile(File parent, String packageName) {
        File file = createNewFile(parent, 'AndroidManifest.xml')

        file << """
<manifest xmlns:android="http://schemas.android.com/apk/res/android" package="$packageName">
    <application />
</manifest>
"""

        file
    }


    protected void assembleBuildStringForAndroidApplication(File buildfile, projectName, applicationId, List<String> dependencies) {

        // project(':${projectName}') {
        buildfile << """
apply plugin: 'android'
apply plugin: org.gradle.api.plugins.nexus.NexusPlugin

android {
    compileSdkVersion ${androidCompileSdkVersion}
    buildToolsVersion "${androidBuildToolsVersion}"

    defaultConfig {
        applicationId "${applicationId}"
        minSdkVersion ${androidDefaultConfigMinSdkVersion}
        targetSdkVersion ${androidDefaultConfigTargetSdkVersion}
        versionCode ${androidDefaultConfigVersionCode}
        versionName "${androidDefaultConfigVersionName}"
    }
    signingConfigs {
        release {
            storeFile file("${androidStoreFile}")
            storePassword "${androidStorePassword}"
            keyAlias "${androidKeyAlias}"
            keyPassword "${androidKeyPassword}"
        }
    }
    buildTypes {
        release {
            debuggable false
            zipAlign true
            runProguard false
            signingConfig signingConfigs.release
        }
    }
    lintOptions {
        abortOnError false
    }
}

dependencies {
    // TODO:
}

"""

    }





}
