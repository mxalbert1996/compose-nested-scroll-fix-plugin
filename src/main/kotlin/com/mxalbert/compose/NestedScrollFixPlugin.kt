package com.mxalbert.compose

import com.android.build.api.instrumentation.*
import com.android.build.api.variant.AndroidComponentsExtension
import com.mxalbert.compose.nestedscrollfix.ScrollingLogicClass
import com.mxalbert.compose.nestedscrollfix.ScrollingLogicClassVisitor
import com.mxalbert.compose.nestedscrollfix.VelocityTrackerClass
import com.mxalbert.compose.nestedscrollfix.VelocityTrackerClassVisitor
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.objectweb.asm.ClassVisitor

@Suppress("unused")
class NestedScrollFixPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        project.afterEvaluate {
            check(project.plugins.hasPlugin("com.android.application")) {
                "${javaClass.simpleName} must be applied to an Android application module."
            }
        }

        project.plugins.withId("com.android.application") {
            project.extensions.configure(AndroidComponentsExtension::class.java) {
                it.onVariants { variant ->
                    variant.instrumentation.apply {
                        transformClassesWith(ClassVisitorFactory::class.java, InstrumentationScope.ALL) {}
                        setAsmFramesComputationMode(
                            FramesComputationMode.COMPUTE_FRAMES_FOR_INSTRUMENTED_METHODS
                        )
                    }
                }
            }
        }
    }

    internal abstract class ClassVisitorFactory :
        AsmClassVisitorFactory<InstrumentationParameters.None> {

        override fun isInstrumentable(classData: ClassData): Boolean = classData.className.let {
            it.startsWith(VelocityTrackerClass) || it == ScrollingLogicClass
        }

        override fun createClassVisitor(
            classContext: ClassContext,
            nextClassVisitor: ClassVisitor
        ): ClassVisitor = classContext.currentClassData.className.let {
            if (it.startsWith(VelocityTrackerClass)) {
                VelocityTrackerClassVisitor(nextClassVisitor, !it.endsWith("Kt"))
            } else {
                ScrollingLogicClassVisitor(nextClassVisitor)
            }
        }
    }
}
