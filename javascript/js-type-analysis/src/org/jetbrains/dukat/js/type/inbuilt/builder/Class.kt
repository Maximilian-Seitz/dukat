package org.jetbrains.dukat.js.type.inbuilt.builder

import org.jetbrains.dukat.js.type.constraint.Constraint
import org.jetbrains.dukat.js.type.constraint.immutable.resolved.BooleanTypeConstraint
import org.jetbrains.dukat.js.type.constraint.immutable.resolved.NoTypeConstraint
import org.jetbrains.dukat.js.type.constraint.immutable.resolved.NumberTypeConstraint
import org.jetbrains.dukat.js.type.constraint.immutable.resolved.StringTypeConstraint
import org.jetbrains.dukat.js.type.constraint.properties.ClassConstraint
import org.jetbrains.dukat.js.type.constraint.properties.FunctionConstraint
import org.jetbrains.dukat.js.type.constraint.properties.ObjectConstraint
import org.jetbrains.dukat.js.type.constraint.properties.PropertyOwnerConstraint
import org.jetbrains.dukat.js.type.property_owner.PropertyOwner

internal fun Class(build: ClassBuilder.() -> Unit) : Lazy<ClassConstraint> {
    return lazy {
        val classConstraint = ClassConstraint(emptyScope)

        ClassBuilder(classConstraint).build()

        classConstraint
    }
}

internal fun Object(build: PropertyOwnerConstraintBuilder.() -> Unit) : Lazy<ObjectConstraint> {
    return lazy {
        val objectConstraint = ObjectConstraint(emptyScope)

        PropertyOwnerConstraintBuilder(objectConstraint).build()

        objectConstraint
    }
}

internal open class PropertyOwnerConstraintBuilder(private val propertyOwnerConstraint: PropertyOwnerConstraint) {
    val boolean = BooleanTypeConstraint
    val number = NumberTypeConstraint
    val string = StringTypeConstraint
    val any = NoTypeConstraint

    protected fun defineFunctionIn(owner: PropertyOwner, returnType: Constraint, definition: Pair<String, List<Pair<String, Constraint>>>) {
        owner[definition.first] = FunctionConstraint(propertyOwnerConstraint, returnType, definition.second)
    }

    operator fun String.invoke(vararg params: Pair<String, Constraint>) : Pair<String, List<Pair<String, Constraint>>> {
        return this to listOf(*params)
    }

    open infix fun Constraint.function(definition: Pair<String, List<Pair<String, Constraint>>>) {
        defineFunctionIn(propertyOwnerConstraint, this, definition)
    }

    open infix fun Constraint.property(name: String) {
        propertyOwnerConstraint[name] = this
    }
}

internal class ClassBuilder(private val classConstraint: ClassConstraint) : PropertyOwnerConstraintBuilder(classConstraint) {
    private val prototype = classConstraint["prototype"] as ObjectConstraint

    val thisType = classConstraint

    infix fun Constraint.staticFunction(definition: Pair<String, List<Pair<String, Constraint>>>) {
        defineFunctionIn(classConstraint, this, definition)
    }

    override infix fun Constraint.function(definition: Pair<String, List<Pair<String, Constraint>>>) {
        defineFunctionIn(prototype, this, definition)
    }

    override infix fun Constraint.property(name: String) {
        prototype[name] = this
    }
}