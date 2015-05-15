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
package org.gradle.api.plugins.nexus.android.helper.builder

import org.gradle.api.plugins.nexus.android.helper.AndroidProjectSettings

/**
 * Builder Class for output to the build.gradle.
 *
 * @author jaken.jarvis@gmail.com
 */
public class AndroidBuildGradleBuilderFeatureApplicationId extends AndroidBuildGradleBuilderBase {
    @Override
    String getAndroidApplicationProjectString(AndroidProjectSettings settings) {
        return """
apply plugin: 'android'
apply plugin: org.gradle.api.plugins.nexus.NexusPlugin

android {
    compileSdkVersion ${settings.androidCompileSdkVersion}
    buildToolsVersion "${settings.androidBuildToolsVersion}"

    defaultConfig {
        applicationId "${settings.androidDefaultConfigApplicationId}"
        minSdkVersion ${settings.androidDefaultConfigMinSdkVersion}
        targetSdkVersion ${settings.androidDefaultConfigTargetSdkVersion}
        versionCode ${settings.androidDefaultConfigVersionCode}
        versionName "${settings.androidDefaultConfigVersionName}"
    }
    signingConfigs {
        release {
            storeFile file("${settings.androidSigningConfigsStoreFile}")
            storePassword "${settings.androidSigningConfigsStorePassword}"
            keyAlias "${settings.androidSigningConfigsKeyAlias}"
            keyPassword "${settings.androidSigningConfigsKeyPassword}"
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

${createDependenciesString(settings)}

        """
    }

    @Override
    String getAndroidLibraryProjectString(AndroidProjectSettings settings) {
        return """
apply plugin: 'android-library'
apply plugin: org.gradle.api.plugins.nexus.NexusPlugin

android {
    compileSdkVersion ${settings.androidCompileSdkVersion}
    buildToolsVersion "${settings.androidBuildToolsVersion}"

    defaultConfig {
        applicationId "${settings.androidDefaultConfigApplicationId}"
        minSdkVersion ${settings.androidDefaultConfigMinSdkVersion}
        targetSdkVersion ${settings.androidDefaultConfigTargetSdkVersion}
        versionCode ${settings.androidDefaultConfigVersionCode}
        versionName "${settings.androidDefaultConfigVersionName}"
    }
    buildTypes {
        release {
            debuggable false
            runProguard false
        }
    }
    lintOptions {
        abortOnError false
    }
}

${createDependenciesString(settings)}

        """
    }

    @Override
    String getJavaLibraryProjectString(AndroidProjectSettings settings) {
        return """
        """
    }
}
