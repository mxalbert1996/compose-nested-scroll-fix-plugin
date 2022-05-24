package com.mxalbert.compose.nestedscrollfix

import org.objectweb.asm.*

internal class LogMethodVisitor(
    api: Int,
    methodVisitor: MethodVisitor?
) : MethodVisitor(api, methodVisitor) {

    private val String?.str: String
        get() = if (this == null) "null" else "\"$this\""

    private fun <T> Array<out T>.join(): String =
        "[${joinToString { it.str }}]"

    private val Any?.str: String
        get() = when (this) {
            is String -> str
            is Array<*> -> join()
            else -> toString()
        }

    override fun visitParameter(name: String?, access: Int) {
        println("visitParameter(${name.str}, $access)")
        super.visitParameter(name, access)
    }

    override fun visitAnnotationDefault(): AnnotationVisitor {
        println("visitAnnotationDefault()")
        return super.visitAnnotationDefault()
    }

    override fun visitAnnotation(descriptor: String?, visible: Boolean): AnnotationVisitor {
        println("visitAnnotation(${descriptor.str}, $visible)")
        return super.visitAnnotation(descriptor, visible)
    }

    override fun visitTypeAnnotation(
        typeRef: Int,
        typePath: TypePath?,
        descriptor: String?,
        visible: Boolean
    ): AnnotationVisitor {
        println("visitTypeAnnotation($typeRef, $typePath, ${descriptor.str}, $visible)")
        return super.visitTypeAnnotation(typeRef, typePath, descriptor, visible)
    }

    override fun visitAnnotableParameterCount(parameterCount: Int, visible: Boolean) {
        println("visitAnnotableParameterCount($parameterCount, $visible)")
        super.visitAnnotableParameterCount(parameterCount, visible)
    }

    override fun visitParameterAnnotation(
        parameter: Int,
        descriptor: String?,
        visible: Boolean
    ): AnnotationVisitor {
        println("visitParameterAnnotation($parameter, ${descriptor.str}, $visible)")
        return super.visitParameterAnnotation(parameter, descriptor, visible)
    }

    override fun visitAttribute(attribute: Attribute?) {
        println("visitAttribute($attribute)")
        super.visitAttribute(attribute)
    }

    override fun visitCode() {
        println("visitCode()")
        super.visitCode()
    }

    override fun visitFrame(
        type: Int,
        numLocal: Int,
        local: Array<out Any>?,
        numStack: Int,
        stack: Array<out Any>?
    ) {
        println("visitFrame($type, $numLocal, ${local?.join()}, $numStack, ${stack?.join()})")
        super.visitFrame(type, numLocal, local, numStack, stack)
    }

    override fun visitInsn(opcode: Int) {
        println("visitInsn($opcode)")
        super.visitInsn(opcode)
    }

    override fun visitIntInsn(opcode: Int, operand: Int) {
        println("visitIntInsn($opcode, $operand)")
        super.visitIntInsn(opcode, operand)
    }

    override fun visitVarInsn(opcode: Int, `var`: Int) {
        println("visitVarInsn($opcode, $`var`)")
        super.visitVarInsn(opcode, `var`)
    }

    override fun visitTypeInsn(opcode: Int, type: String?) {
        println("visitTypeInsn($opcode, ${type.str})")
        super.visitTypeInsn(opcode, type)
    }

    override fun visitFieldInsn(
        opcode: Int,
        owner: String?,
        name: String?,
        descriptor: String?
    ) {
        println("visitFieldInsn($opcode, ${owner.str}, ${name.str}, ${descriptor.str})")
        super.visitFieldInsn(opcode, owner, name, descriptor)
    }

    override fun visitMethodInsn(
        opcode: Int,
        owner: String?,
        name: String?,
        descriptor: String?,
        isInterface: Boolean
    ) {
        println("visitMethodInsn($opcode, ${owner.str}, ${name.str}, ${descriptor.str}, $isInterface)")
        super.visitMethodInsn(opcode, owner, name, descriptor, isInterface)
    }

    override fun visitInvokeDynamicInsn(
        name: String?,
        descriptor: String?,
        bootstrapMethodHandle: Handle?,
        vararg bootstrapMethodArguments: Any?
    ) {
        println("visitInvokeDynamicInsn(${name.str}, ${descriptor.str}, $bootstrapMethodHandle, ${bootstrapMethodArguments.join()})")
        super.visitInvokeDynamicInsn(
            name,
            descriptor,
            bootstrapMethodHandle,
            *bootstrapMethodArguments
        )
    }

    override fun visitJumpInsn(opcode: Int, label: Label?) {
        println("visitJumpInsn($opcode, $label)")
        super.visitJumpInsn(opcode, label)
    }

    override fun visitLabel(label: Label?) {
        println("visitLabel($label)")
        super.visitLabel(label)
    }

    override fun visitLdcInsn(value: Any?) {
        println("visitLdcInsn(${value.str})")
        super.visitLdcInsn(value)
    }

    override fun visitIincInsn(`var`: Int, increment: Int) {
        println("visitIincInsn($`var`, $increment)")
        super.visitIincInsn(`var`, increment)
    }

    override fun visitTableSwitchInsn(min: Int, max: Int, dflt: Label?, vararg labels: Label?) {
        println("visitTableSwitchInsn($min, $max, $dflt, ${labels.joinToString()})")
        super.visitTableSwitchInsn(min, max, dflt, *labels)
    }

    override fun visitLookupSwitchInsn(
        dflt: Label?,
        keys: IntArray?,
        labels: Array<out Label>?
    ) {
        println("visitLookupSwitchInsn($dflt, [${keys?.joinToString()}], ${labels?.join()})")
        super.visitLookupSwitchInsn(dflt, keys, labels)
    }

    override fun visitMultiANewArrayInsn(descriptor: String?, numDimensions: Int) {
        println("visitMultiANewArrayInsn(${descriptor.str}, $numDimensions)")
        super.visitMultiANewArrayInsn(descriptor, numDimensions)
    }

    override fun visitInsnAnnotation(
        typeRef: Int,
        typePath: TypePath?,
        descriptor: String?,
        visible: Boolean
    ): AnnotationVisitor {
        println("visitInsnAnnotation($typeRef, $typePath, ${descriptor.str}, $visible)")
        return super.visitInsnAnnotation(typeRef, typePath, descriptor, visible)
    }

    override fun visitTryCatchBlock(
        start: Label?,
        end: Label?,
        handler: Label?,
        type: String?
    ) {
        println("visitTryCatchBlock($start, $end, $handler, ${type.str})")
        super.visitTryCatchBlock(start, end, handler, type)
    }

    override fun visitTryCatchAnnotation(
        typeRef: Int,
        typePath: TypePath?,
        descriptor: String?,
        visible: Boolean
    ): AnnotationVisitor {
        println("visitTryCatchAnnotation($typeRef, $typePath, ${descriptor.str}, $visible)")
        return super.visitTryCatchAnnotation(typeRef, typePath, descriptor, visible)
    }

    override fun visitLocalVariable(
        name: String?,
        descriptor: String?,
        signature: String?,
        start: Label?,
        end: Label?,
        index: Int
    ) {
        println("visitLocalVariable(${name.str}, ${descriptor.str}, ${signature.str}, $start, $end, $index)")
        super.visitLocalVariable(name, descriptor, signature, start, end, index)
    }

    override fun visitLocalVariableAnnotation(
        typeRef: Int,
        typePath: TypePath?,
        start: Array<out Label>?,
        end: Array<out Label>?,
        index: IntArray?,
        descriptor: String?,
        visible: Boolean
    ): AnnotationVisitor {
        println("visitLocalVariableAnnotation($typeRef, $typePath, ${start?.join()}, ${end?.join()}, [${index?.joinToString()}], ${descriptor.str}, $visible)")
        return super.visitLocalVariableAnnotation(
            typeRef,
            typePath,
            start,
            end,
            index,
            descriptor,
            visible
        )
    }

    override fun visitLineNumber(line: Int, start: Label?) {
        println("visitLineNumber($line, $start)")
        super.visitLineNumber(line, start)
    }

    override fun visitMaxs(maxStack: Int, maxLocals: Int) {
        println("visitMaxs($maxStack, $maxLocals)")
        super.visitMaxs(maxStack, maxLocals)
    }

    override fun visitEnd() {
        println("visitEnd()")
        super.visitEnd()
    }
}
