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
package org.gradle.api.plugins.nexus

import org.gradle.tooling.BuildLauncher
import org.gradle.tooling.GradleConnector
import org.gradle.tooling.ProjectConnection
import org.gradle.tooling.model.GradleProject
import spock.lang.Specification

import static org.spockframework.util.Assert.fail

/**
 * Abstract integregration test using Gradle's tooling API.
 *
 * @author Benjamin Muschko
 */
abstract class AbstractIntegrationTest extends Specification {
    File integTestDir
    File buildFile

    def setup() {
        integTestDir = new File('build/integTest')

        if(!integTestDir.deleteDir()) {
            fail('Unable to delete integration test directory.')
        }

        if(!integTestDir.mkdirs()) {
            fail('Unable to create integration test directory.')
        }

        onSetup()
    }

    protected void onSetup() {
        buildFile = createNewFile(integTestDir, 'build.gradle')
        buildFile << """
buildscript {
    dependencies {
        classpath files('../classes/main')
    }
}

"""
    }

   protected File createNewDir(File parent, String dirname) {
        File dir = new File(parent, dirname)

        if(!dir.exists()) {
            if(!dir.mkdirs()) {
                fail("Unable to create new test directory $dir.canonicalPath.")
            }
        }

        dir
    }

    protected File createNewFile(File parent, String filename) {
        File file = new File(parent, filename)

        if(!file.exists()) {
            if(!file.createNewFile()) {
                fail("Unable to create new test file $file.canonicalPath.")
            }
        }

        file
    }

    protected File copyFile(File source, File target) {
        if(!source.exists()) {
            fail("Unable to copy test file because the source file does not exist: $source.canonicalPath")
        }
        if(target.exists()) {
            fail("Unable to copy test file because the target file exists: $target.canonicalPath")
        }
        target << source.readBytes()
    }

    protected void assertExistingFiles(File dir, List<String> requiredFilenames) {
        assertExistingDirectory(dir)
        def dirFileNames = dir.listFiles()*.name

        requiredFilenames.each { filename ->
            assert dirFileNames.find { it ==~ filename }
        }
    }

    protected void assertNoSignatureFiles(File dir) {
        assertExistingDirectory(dir)
        def dirFileNames = dir.listFiles()*.name

        dirFileNames.each { filename ->
            assert !filename.endsWith('.asc')
        }
    }

    private void assertExistingDirectory(File dir) {
        if(!dir || !dir.exists()) {
            fail("Unable to check target directory '${dir?.canonicalPath}' for files.")
        }
    }

    protected void assertCorrectPomXml(File pomFile) {
        println pomFile.text
        def pomXml = new XmlSlurper().parse(pomFile)
        assert pomXml.name.text() == 'myapp'
        assert pomXml.description.text() == 'My application'
        assert pomXml.inceptionYear.text() == '2012'
        def developer = pomXml.developers.developer[0]
        assert developer.id.text() == 'bmuschko'
        assert developer.name.text() == 'Benjamin Muschko'
        assert developer.email.text() == 'benjamin.muschko@gmail.com'
    }

    protected GradleProject runTasks(File projectDir, String... tasks) {
        runTasks(null, projectDir, tasks)
    }

    protected GradleProject runTasks(String gradleVersion, File projectDir, String... tasks) {
        ProjectConnection connection
        if(gradleVersion == null) {
            connection = GradleConnector.newConnector()
                .forProjectDirectory(projectDir)
                .connect()
        } else {
            connection = GradleConnector.newConnector()
                .forProjectDirectory(projectDir)
                .useGradleVersion(gradleVersion)
                .connect()
        }

        try {
            BuildLauncher builder = connection.newBuild()
            builder.forTasks(tasks).run()
            return connection.getModel(GradleProject)
        }
        finally {
            connection?.close()
        }
    }
}
