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

import org.gradle.api.plugins.nexus.android.helper.AndroidBuildGradleBuilderInterface
import org.gradle.api.plugins.nexus.android.helper.AndroidProjectSettings

/**
 * Builder Base Class for output to the build.gradle.
 *
 * @author jaken.jarvis@gmail.com
 */
public abstract class AndroidBuildGradleBuilderBase implements AndroidBuildGradleBuilderInterface {
    public AndroidBuildGradleBuilderBase() {
    }

    @Override
    String getRootBuildGradleString(AndroidProjectSettings settings) {
        return """
buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath files('../classes/main')
        classpath 'com.jakewharton.sdkmanager:gradle-plugin:${settings.buildscriptDependenciesAndroidSdkManagerPluginVersion}'
        classpath 'com.android.tools.build:gradle:${settings.androidGradlePluginVersion.getPluginVersion()}'
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

    @Override
    String getEmptyAndroidManifestString(AndroidProjectSettings settings) {
        return """
<manifest xmlns:android="http://schemas.android.com/apk/res/android" package="${settings.androidDefaultConfigApplicationId}">
    <application />
</manifest>
        """
    }

    protected String createDependenciesString(AndroidProjectSettings settings) {
        def dependenciesString = ""
        settings.projectDependencies.each { dependencie ->
            dependenciesString += "    " + dependencie + "\n"
        }
        return """
dependencies {
${dependenciesString}
}
        """
    }

}
