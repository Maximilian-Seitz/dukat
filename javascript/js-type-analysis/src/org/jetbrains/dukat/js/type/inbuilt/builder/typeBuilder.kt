package org.jetbrains.dukat.js.type.inbuilt.builder

import org.jetbrains.dukat.js.type.constraint.Constraint
import org.jetbrains.dukat.js.type.constraint.immutable.resolved.BooleanTypeConstraint
import org.jetbrains.dukat.js.type.constraint.immutable.resolved.NoTypeConstraint
import org.jetbrains.dukat.js.type.constraint.immutable.resolved.NumberTypeConstraint
import org.jetbrains.dukat.js.type.constraint.immutable.resolved.StringTypeConstraint
import org.jetbrains.dukat.js.type.constraint.immutable.resolved.VoidTypeConstraint
import org.jetbrains.dukat.js.type.constraint.properties.ClassConstraint
import org.jetbrains.dukat.js.type.constraint.properties.FunctionConstraint
import org.jetbrains.dukat.js.type.constraint.properties.ObjectConstraint
import org.jetbrains.dukat.js.type.propertyOwner.PropertyOwner
import org.jetbrains.dukat.js.type.propertyOwner.Scope

private typealias Parameter = Pair<String, Constraint>

private val typeOwner by lazy { TypeOwner() }

internal fun jsFunction(vararg params: Parameter, returnType: TypeOwner.() -> Constraint) : FunctionConstraint {
    return FunctionConstraint(emptyScope, listOf(FunctionConstraint.Overload(
            returnConstraints = typeOwner.returnType(),
            parameterConstraints = params.toList()
    )))
}

internal fun jsObject(owner: Scope = emptyScope, build: PropertyOwnerBuilder.() -> Unit) : ObjectConstraint {
    val objectConstraint = ObjectConstraint(owner)
    PropertyOwnerBuilder(objectConstraint).build()
    return objectConstraint
}

internal fun jsClass(owner: Scope = emptyScope, build: ClassBuilder.() -> Unit) : ClassConstraint {
    val classConstraint = ClassConstraint(owner)
    ClassBuilder(classConstraint).build()
    return classConstraint
}

internal fun jsEnvironment(build: PropertyOwnerBuilder.() -> Unit) : Scope {
    val env = Scope()
    PropertyOwnerBuilder(env).build()
    return env
}

internal open class TypeOwner {
    val void = VoidTypeConstraint
    val boolean = BooleanTypeConstraint
    val number = NumberTypeConstraint
    val string = StringTypeConstraint
    val any = NoTypeConstraint
}

internal open class PropertyOwnerBuilder(private val propertyOwner: PropertyOwner) : TypeOwner() {
    fun vararg(argProvider: (Int) -> Parameter) = Array(20) { argProvider(it + 1) }

    protected fun defineFunctionIn(owner: PropertyOwner, name: String, returnType: Constraint, params: List<Parameter>) {
        owner[name] = FunctionConstraint(propertyOwner, listOf(FunctionConstraint.Overload(returnType, params)))
    }

    protected open fun registerFunction(name: String, returnType: Constraint, params: List<Parameter>) {
        defineFunctionIn(propertyOwner, name, returnType, params)
    }

    operator fun String.invoke(vararg params: Parameter, returnType: () -> Constraint) {
        registerFunction(this, returnType(), listOf(*params))
    }

    open operator fun String.minus(type: Constraint) {
        propertyOwner[this] = type
    }
}

internal class ClassBuilder(private val classConstraint: ClassConstraint) : PropertyOwnerBuilder(classConstraint) {
    private val prototype = classConstraint["prototype"] as ObjectConstraint

    val thisType = NoTypeConstraint //TODO implement "this" type, to return instance of parent class

    fun static(modify: PropertyOwnerBuilder.() -> Unit) {
        PropertyOwnerBuilder(classConstraint).modify()
    }

    override fun registerFunction(name: String, returnType: Constraint, params: List<Parameter>) {
        defineFunctionIn(prototype, name, returnType, params)
    }

    fun constructor(vararg params: Parameter) {
        classConstraint.constructorConstraint = FunctionConstraint(classConstraint, listOf(FunctionConstraint.Overload(void, listOf(*params))))
    }

    override operator fun String.minus(type: Constraint) {
        prototype[this] = type
    }
}