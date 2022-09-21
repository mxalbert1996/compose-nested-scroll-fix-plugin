package com.mxalbert.compose

import com.google.testing.junit.testparameterinjector.junit5.TestParameter
import com.google.testing.junit.testparameterinjector.junit5.TestParameterInjectorTest
import org.gradle.testkit.runner.GradleRunner
import org.junit.jupiter.api.Assertions.assertFalse
import java.io.File

class NestedScrollFixPluginBuildTest {

    @TestParameterInjectorTest
    fun build(@TestParameter("1.1.1", "1.2.1") composeVersion: String) {
        val output = GradleRunner.create()
            .withProjectDir(File("src/test/project"))
            .withDebug(true)
            .withArguments(
                "-PpluginVersion=${System.getProperty("pluginVersion")!!}",
                "-PcomposeVersion=$composeVersion",
                "clean",
                "assemble",
                "--stacktrace"
            )
            .forwardOutput()
            .build()
            .output
        assertFalse(output.contains("warning", ignoreCase = true))
        assertFalse(output.contains("error", ignoreCase = true))
    }
}
