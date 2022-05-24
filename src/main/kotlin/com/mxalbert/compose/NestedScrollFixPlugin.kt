package com.mxalbert.compose

import com.mxalbert.compose.nestedscrollfix.ScrollingLogicClass
import com.mxalbert.compose.nestedscrollfix.ScrollingLogicClassVisitor
import com.mxalbert.compose.nestedscrollfix.VelocityTrackerClass
import com.mxalbert.compose.nestedscrollfix.VelocityTrackerClassVisitor
import com.android.build.api.instrumentation.*
import com.android.build.api.variant.AndroidComponentsExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.objectweb.asm.ClassVisitor

class NestedScrollFixPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        val androidComponents = project.extensions.getByType(AndroidComponentsExtension::class.java)

        androidComponents.onVariants { variant ->
            variant.instrumentation.apply {
                transformClassesWith(ClassVisitorFactory::class.java, InstrumentationScope.ALL) {}
                setAsmFramesComputationMode(
                    FramesComputationMode.COMPUTE_FRAMES_FOR_INSTRUMENTED_METHODS
                )
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
