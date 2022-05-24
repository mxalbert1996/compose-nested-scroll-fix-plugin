package com.mxalbert.compose.nestedscrollfix

import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes

internal class ScrollingLogicClassVisitor(
    classVisitor: ClassVisitor
) : ClassVisitor(Opcodes.ASM7, classVisitor) {

    override fun visitMethod(
        access: Int,
        name: String,
        descriptor: String,
        signature: String?,
        exceptions: Array<out String>?
    ): MethodVisitor {
        val mv = super.visitMethod(access, name, descriptor, signature, exceptions)
        return if (name.startsWith("dispatchScroll"))
            DispatchScrollMethodVisitor(api, mv, access, name, descriptor) else mv
    }
}
