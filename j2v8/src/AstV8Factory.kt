package org.jetbrains.dukat.ast.j2v8

import com.eclipsesource.v8.Releasable
import com.eclipsesource.v8.V8Array
import com.eclipsesource.v8.V8Object
import com.eclipsesource.v8.utils.V8ObjectUtils
import org.jetbrains.dukat.ast.model.AstNode
import org.jetbrains.dukat.ast.model.Declaration
import org.jetbrains.dukat.ast.model.Expression
import org.jetbrains.dukat.ast.model.ParameterDeclaration
import org.jetbrains.dukat.ast.model.ParameterValue
import org.jetbrains.dukat.ast.toAst

private fun V8Object.toMap(): Map<String, Any?> = V8ObjectUtils.toMap(this)
private fun <T:AstNode> V8Object.toAst(): T = toMap().toAst()

private fun V8Array.toArray(): Array<Map<String, Any?>> {
    val res = mutableListOf<Map<String, Any?>>()
    for (i in 0 until this.length()) {
        val param = this.get(i)
        val map = (param as V8Object).toMap()
        if (param is Releasable) {
            param.release()
        }

        res.add(map)
    }

    return res.toTypedArray()
}

class AstV8Factory(private val astFactory: AstJ2V8Factory) {

    fun createExpression(kind: V8Object, meta: String) = astFactory.createExpression(kind.toAst(), meta)

    fun declareVariable(name: String, type: V8Object): V8Object = astFactory.declareVariable(name, type.toAst())

    fun createFunctionDeclaration(name: String, parameters: V8Array, type: V8Object): V8Object {
        val res = parameters.toArray().map { it.toAst<ParameterDeclaration>()}


        return astFactory.createFunctionDeclaration(
                name,
                res.toTypedArray(),
                type.toAst()
        )
    }

    fun createFunctionTypeDeclaration(parameters: V8Array, type: V8Object): V8Object {
        val res = parameters.toArray().map { it.toAst<ParameterDeclaration>() }

        return astFactory.createFunctionTypeDeclaration(
                res.toTypedArray(),
                type.toAst()
        )
    }

    fun createTypeDeclaration(value: String, params: V8Array) = astFactory.createTypeDeclaration(value, params.toArray().map { type -> type.toAst<ParameterValue>() }.toTypedArray())

    fun createParameterDeclaration(name: String, type: V8Object, initializer: V8Object?) =
            astFactory.createParameterDeclaration(
                    name, type.toAst(),
                    if (initializer == null) null else initializer.toAst<Expression>()
            )

    fun createDocumentRoot(declarations: V8Array) =
            astFactory.createDocumentRoot(declarations.toArray().map{declaration -> declaration.toAst<Declaration>()}.toTypedArray())
}