/*
 * Copyright 2013 the original author or authors.
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

import org.gradle.tooling.model.GradleProject

/**
 * Nexus plugin artifact creation integration test for android application.
 *
 * @author jaken.jarvis@gmail.com
 */
class SingleAndroidApplicationProjectArtifactIntegrationTest extends SingleAndroidApplicationProjectBuildIntegrationTest {
    def "Creates all configured APK for default configuration"() {
        Map<String, List<String>> mapExpectedFilenames = [:]

        when:
        def projectName = "androidapp"
        mapExpectedFilenames += [ (projectName) : ["${projectName}-debug.apk", "${projectName}-debug-unaligned.apk",
                                                   "${projectName}-release.apk", "${projectName}-release-unaligned.apk",
                                                   "${projectName}-release.apk.asc"
        ]]

        subprojects[projectName].projectBuildGradleFile << """
nexus {
    attachSources = false
    attachTests = false
    attachJavadoc = false
}
"""

        String gradleVersion = rootSettings.androidGradlePluginVersion.getGradleVersion()
        GradleProject project = runTasks(gradleVersion, integTestDir, 'assemble')

        then:
        subprojects.each { name, settings ->
            def expectedFilenames = mapExpectedFilenames[name]
            assertExistingFiles(settings.outputsArtifactDir, expectedFilenames)
        }
    }

}


// gradlew clean build -DintegrationTest.single=SingleAndroidApplicationProjectArtifactIntegrationTest