package com.mxalbert.compose.nestedscrollfix

import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.Type
import org.objectweb.asm.commons.GeneratorAdapter
import org.objectweb.asm.commons.Method

internal class VelocityTrackerClassVisitor(
    classVisitor: ClassVisitor,
    private val addMethod: Boolean
) : ClassVisitor(Opcodes.ASM7, classVisitor) {

    override fun visitMethod(
        access: Int,
        name: String,
        descriptor: String,
        signature: String?,
        exceptions: Array<out String>?
    ): MethodVisitor? {
        val mv = super.visitMethod(access, name, descriptor, signature, exceptions)
        return if (name.startsWith("addPointerInputChange"))
            AddPointerInputChangeMethodVisitor(api, mv, access, name, descriptor) else mv
    }

    override fun visitEnd() {
        if (addMethod) {
            val mv = visitMethod(
                Opcodes.ACC_PUBLIC or Opcodes.ACC_FINAL,
                VelocityTracker_addPositionChange.name,
                VelocityTracker_addPositionChange.descriptor,
                null,
                null
            )
            GeneratorAdapter(
                mv,
                Opcodes.ACC_PUBLIC or Opcodes.ACC_FINAL,
                VelocityTracker_addPositionChange.name,
                VelocityTracker_addPositionChange.descriptor
            ).createAddPositionChangeMethod()
        }
        super.visitEnd()
    }

    private fun GeneratorAdapter.createAddPositionChangeMethod() {
        val pointAtTime =
            Type.getObjectType("androidx/compose/ui/input/pointer/util/PointAtTime")
        val pointAtTimeArray =
            Type.getObjectType("[Landroidx/compose/ui/input/pointer/util/PointAtTime;")

        val l0 = newLabel()
        val l1 = newLabel()
        val l2 = newLabel()
        val l3 = newLabel()
        val l4 = newLabel()
        val l5 = newLabel()
        val l6 = newLabel()

        loadArg(1)
        invokeStatic(Offset, Method.getMethod("float getX-impl (long)"))
        push(0f)
        ifCmp(Type.FLOAT_TYPE, GeneratorAdapter.NE, l0)
        push(1)
        goTo(l1)
        mark(l0)
        push(0)
        mark(l1)
        ifZCmp(GeneratorAdapter.EQ, l4)
        loadArg(1)
        invokeStatic(Offset, Method.getMethod("float getY-impl (long)"))
        push(0f)
        ifCmp(Type.FLOAT_TYPE, GeneratorAdapter.NE, l2)
        push(1)
        goTo(l3)
        mark(l2)
        push(0)
        mark(l3)
        ifZCmp(GeneratorAdapter.EQ, l4)
        returnValue()
        mark(l4)
        loadThis()
        getField(VelocityTracker, "samples", pointAtTimeArray)
        loadThis()
        getField(VelocityTracker, "index", Type.INT_TYPE)
        arrayLoad(pointAtTime)
        dup()
        ifNull(l5)
        invokeVirtual(pointAtTime, Method.getMethod("long getPoint-F1C5BW0 ()"))
        goTo(l6)
        mark(l5)
        pop()
        getStatic(Offset, "Companion", OffsetCompanion)
        invokeVirtual(OffsetCompanion, Offset_Zero)
        mark(l6)
        val offsetVar = newLocal(Type.LONG_TYPE)
        storeLocal(offsetVar, Type.LONG_TYPE)
        loadThis()
        loadThis()
        getField(VelocityTracker, "index", Type.INT_TYPE)
        push(1)
        math(GeneratorAdapter.ADD, Type.INT_TYPE)
        push(20)
        math(GeneratorAdapter.REM, Type.INT_TYPE)
        putField(VelocityTracker, "index", Type.INT_TYPE)
        loadThis()
        getField(VelocityTracker, "samples", pointAtTimeArray)
        loadThis()
        getField(VelocityTracker, "index", Type.INT_TYPE)
        newInstance(pointAtTime)
        dup()
        loadLocal(offsetVar)
        loadArg(1)
        invokeStatic(Offset, Offset_plus)
        loadArg(0)
        push(null as Type?)
        invokeConstructor(
            pointAtTime,
            Method.getMethod("void <init> (long, long, kotlin.jvm.internal.DefaultConstructorMarker)")
        )
        arrayStore(pointAtTime)
        returnValue()
        endMethod()
    }
}
