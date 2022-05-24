package com.mxalbert.compose.nestedscrollfix

import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Type
import org.objectweb.asm.commons.GeneratorAdapter
import org.objectweb.asm.commons.Method

internal class AddPointerInputChangeMethodVisitor(
    api: Int,
    methodVisitor: MethodVisitor,
    access: Int,
    name: String,
    descriptor: String?
) : GeneratorAdapter(api, methodVisitor, access, name, descriptor) {

    private var intrinsicsCount = 0
    private var previousPositionVar = 0
    private var historicalChangeVar = 0
    private var updatePreviousPositionInserted = false

    override fun setLocalType(local: Int, type: Type) {
        super.setLocalType(local, type)
        if (type.sort == Type.OBJECT) historicalChangeVar = local
    }

    override fun visitMethodInsn(
        opcode: Int,
        owner: String,
        name: String,
        descriptor: String?,
        isInterface: Boolean
    ) {
        if (owner == VelocityTracker.internalName && name.startsWith("addPosition")) {
            insertMinusPreviousPosition()
            if (!updatePreviousPositionInserted) {
                insertUpdatePreviousPosition()
                updatePreviousPositionInserted = true
            }
            return
        }
        super.visitMethodInsn(opcode, owner, name, descriptor, isInterface)
        if (owner == "kotlin/jvm/internal/Intrinsics" && name == "checkNotNullParameter") {
            intrinsicsCount++
            if (intrinsicsCount == 2) {
                insertPreviousPositionVar()
            }
        }
    }

    private fun insertPreviousPositionVar() {
        previousPositionVar = newLocal(Type.LONG_TYPE)
        push(0L)
        storeLocal(previousPositionVar)
        loadArg(1)
        invokeVirtual(
            Type.getObjectType("androidx/compose/ui/input/pointer/PointerInputChange"),
            Method.getMethod("long getPreviousPosition-F1C5BW0 ()")
        )
        storeLocal(previousPositionVar)
    }

    private fun insertMinusPreviousPosition() {
        loadLocal(previousPositionVar)
        invokeStatic(Offset, Offset_minus)
        invokeVirtual(VelocityTracker, VelocityTracker_addPositionChange)
    }

    private fun insertUpdatePreviousPosition() {
        val historicalChange =
            Type.getObjectType("androidx/compose/ui/input/pointer/HistoricalChange")
        loadLocal(historicalChangeVar, historicalChange)
        invokeVirtual(historicalChange, Method.getMethod("long getPosition-F1C5BW0 ()"))
        storeLocal(previousPositionVar)
    }
}
