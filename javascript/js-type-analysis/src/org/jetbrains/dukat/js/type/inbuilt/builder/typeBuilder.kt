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
import org.jetbrains.dukat.js.type.constraint.properties.PropertyOwnerConstraint
import org.jetbrains.dukat.js.type.propertyOwner.PropertyOwner
import org.jetbrains.dukat.js.type.propertyOwner.Scope

private typealias Parameter = Pair<String, Constraint>

internal fun jsFunction(vararg params: Parameter, returnType: () -> Constraint) : FunctionConstraint {
    return FunctionConstraint(emptyScope, listOf(FunctionConstraint.Overload(
            returnConstraints = returnType(),
            parameterConstraints = params.toList()
    )))
}

internal fun jsClass(owner: Scope = emptyScope, build: ClassBuilder.() -> Unit) : Lazy<ClassConstraint> {
    return lazy {
        val classConstraint = ClassConstraint(owner)

        ClassBuilder(classConstraint).build()

        classConstraint
    }
}

internal fun jsObject(owner: Scope = emptyScope, build: PropertyOwnerConstraintBuilder.() -> Unit) : Lazy<ObjectConstraint> {
    return lazy {
        val objectConstraint = ObjectConstraint(owner)

        PropertyOwnerConstraintBuilder(objectConstraint).build()

        objectConstraint
    }
}

internal open class PropertyOwnerConstraintBuilder(private val propertyOwnerConstraint: PropertyOwnerConstraint) {
    val void = VoidTypeConstraint
    val boolean = BooleanTypeConstraint
    val number = NumberTypeConstraint
    val string = StringTypeConstraint
    val any = NoTypeConstraint

    fun vararg(argProvider: (Int) -> Parameter) = Array(20) { argProvider(it + 1) }

    protected fun defineFunctionIn(owner: PropertyOwner, name: String, returnType: Constraint, params: List<Parameter>) {
        owner[name] = FunctionConstraint(propertyOwnerConstraint, listOf(FunctionConstraint.Overload(returnType, params)))
    }

    protected open fun registerFunction(name: String, returnType: Constraint, params: List<Parameter>) {
        defineFunctionIn(propertyOwnerConstraint, name, returnType, params)
    }

    operator fun String.invoke(vararg params: Parameter, returnType: () -> Constraint) {
        registerFunction(this, returnType(), listOf(*params))
    }

    open infix fun String.isType(type: Constraint) {
        propertyOwnerConstraint[this] = type
    }
}

internal class ClassBuilder(private val classConstraint: ClassConstraint) : PropertyOwnerConstraintBuilder(classConstraint) {
    private val prototype = classConstraint["prototype"] as ObjectConstraint

    val thisType = classConstraint

    fun static(modify: PropertyOwnerConstraintBuilder.() -> Unit) {
        PropertyOwnerConstraintBuilder(classConstraint).modify()
    }

    override fun registerFunction(name: String, returnType: Constraint, params: List<Parameter>) {
        defineFunctionIn(prototype, name, returnType, params)
    }

    fun constructor(vararg params: Parameter) {
        classConstraint.constructorConstraint = FunctionConstraint(classConstraint, listOf(FunctionConstraint.Overload(void, listOf(*params))))
    }

    override infix fun String.isType(type: Constraint) {
        prototype[this] = type
    }
}