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
package org.gradle.api.plugins.nexus.android.singleproject

import org.gradle.api.plugins.nexus.android.AbstractAndroidIntegrationTest

/**
 * Single android application project integration test.
 *
 * @author jaken.jarvis@gmail.com
 */
abstract class SingleAndroidApplicationProjectBuildIntegrationTest extends AbstractAndroidIntegrationTest {
    Map<String, List<String>> dependencies = [:]
    Map<String, File> projectBuildFiles = [:]

    List<String> subprojects = []
    File settingsFile

    def setup() {
        dependencies += [androidapp : []]
        subprojects = dependencies.keySet() as String[]

        settingsFile = createNewFile(integTestDir, 'settings.gradle')
        subprojects.each { subproject ->
            settingsFile << "include '$subproject'\n"
            File projectDir = createNewDir(integTestDir, subproject)

            projectBuildFiles += [(subproject) : createNewFile(projectDir, 'build.gradle')]

            def applicationId = "${androidDefaultConfigApplicationId}.${subproject}"
            assembleBuildStringForAndroidApplication(projectBuildFiles[subproject], subproject, applicationId, dependencies[subproject])

            copyFile(defaultAndroidKeyStoreFile, new File(projectDir, androidStoreFile))

            File projectMainSrcDir = createNewDir(projectDir, 'src/main')
            createNewEmptyAndroidManifestFile(projectMainSrcDir, applicationId)
        }
    }
}
