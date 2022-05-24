package com.mxalbert.compose.nestedscrollfix

import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.Type
import org.objectweb.asm.commons.GeneratorAdapter

internal class DispatchScrollMethodVisitor(
    api: Int,
    methodVisitor: MethodVisitor,
    access: Int,
    name: String,
    descriptor: String?
) : GeneratorAdapter(api, methodVisitor, access, name, descriptor) {

    private var axisConsumedVar = 0
    private var leftForParentVar = 0
    private var parentConsumedVar = 0
    private var resultVar = 0
    private var step = 0

    override fun setLocalType(local: Int, type: Type) {
        super.setLocalType(local, type)
        if (type.sort == Type.LONG) {
            axisConsumedVar = leftForParentVar
            leftForParentVar = parentConsumedVar
            parentConsumedVar = local
        }
    }

    override fun visitMethodInsn(
        opcode: Int,
        owner: String,
        name: String,
        descriptor: String?,
        isInterface: Boolean
    ) {
        if (step == 4 && owner == Offset.internalName && name.startsWith("minus")) {
            step = 5
            loadLocal(resultVar)
        } else {
            super.visitMethodInsn(opcode, owner, name, descriptor, isInterface)
            if (owner == "androidx/compose/ui/input/nestedscroll/NestedScrollDispatcher" &&
                name.startsWith("dispatchPostScroll")
            ) step = 1
        }
    }

    override fun visitVarInsn(opcode: Int, varIndex: Int) {
        if (step == 4 && opcode == Opcodes.LLOAD) {
            return
        } else if (step == 5 && opcode == Opcodes.LLOAD) {
            step = 6
            loadLocal(resultVar)
        } else {
            super.visitVarInsn(opcode, varIndex)
            if (step == 1) {
                step = 2
                loadLocal(leftForParentVar)
                loadLocal(parentConsumedVar)
                invokeStatic(Offset, Offset_minus)
                resultVar = newLocal(Type.LONG_TYPE)
                storeLocal(resultVar)
                step = 3
            } else if (step == 3 && opcode == Opcodes.LLOAD) {
                step = 4
            }
        }
    }
}
