package org.jetbrains.dukat.js.type.constraint.resolution

import org.jetbrains.dukat.astCommon.IdentifierEntity
import org.jetbrains.dukat.js.identifiers.JavaScriptIdentifiers
import org.jetbrains.dukat.js.type.constraint.Constraint
import org.jetbrains.dukat.js.type.constraint.composite.CompositeConstraint
import org.jetbrains.dukat.js.type.constraint.composite.UnionTypeConstraint
import org.jetbrains.dukat.js.type.constraint.immutable.CallableConstraint
import org.jetbrains.dukat.js.type.constraint.immutable.resolved.BigIntTypeConstraint
import org.jetbrains.dukat.js.type.constraint.immutable.resolved.BooleanTypeConstraint
import org.jetbrains.dukat.js.type.constraint.immutable.resolved.NoTypeConstraint
import org.jetbrains.dukat.js.type.constraint.immutable.resolved.NumberTypeConstraint
import org.jetbrains.dukat.js.type.constraint.immutable.resolved.StringTypeConstraint
import org.jetbrains.dukat.js.type.constraint.immutable.resolved.ThrowConstraint
import org.jetbrains.dukat.js.type.constraint.immutable.resolved.VoidTypeConstraint
import org.jetbrains.dukat.js.type.constraint.properties.ClassConstraint
import org.jetbrains.dukat.js.type.constraint.properties.FunctionConstraint
import org.jetbrains.dukat.js.type.constraint.properties.ObjectConstraint
import org.jetbrains.dukat.js.type.propertyOwner.PropertyOwner
import org.jetbrains.dukat.js.type.type.ANY_NULLABLE_TYPE
import org.jetbrains.dukat.js.type.type.BOOLEAN_TYPE
import org.jetbrains.dukat.js.type.type.NOTHING_TYPE
import org.jetbrains.dukat.js.type.type.NUMBER_TYPE
import org.jetbrains.dukat.js.type.type.STRING_TYPE
import org.jetbrains.dukat.js.type.type.VOID_TYPE
import org.jetbrains.dukat.panic.raiseConcern
import org.jetbrains.dukat.tsmodel.CallSignatureDeclaration
import org.jetbrains.dukat.tsmodel.ClassDeclaration
import org.jetbrains.dukat.tsmodel.ConstructorDeclaration
import org.jetbrains.dukat.tsmodel.ExportAssignmentDeclaration
import org.jetbrains.dukat.tsmodel.FunctionDeclaration
import org.jetbrains.dukat.tsmodel.MemberDeclaration
import org.jetbrains.dukat.tsmodel.ModifierDeclaration
import org.jetbrains.dukat.tsmodel.ParameterDeclaration
import org.jetbrains.dukat.tsmodel.PropertyDeclaration
import org.jetbrains.dukat.tsmodel.TopLevelDeclaration
import org.jetbrains.dukat.tsmodel.VariableDeclaration
import org.jetbrains.dukat.tsmodel.WithUidDeclaration
import org.jetbrains.dukat.tsmodel.types.FunctionTypeDeclaration
import org.jetbrains.dukat.tsmodel.types.ObjectLiteralDeclaration
import org.jetbrains.dukat.tsmodel.types.ParameterValueDeclaration
import org.jetbrains.dukat.tsmodel.types.UnionTypeDeclaration

private val DECLARE_MODIFIERS = listOf(ModifierDeclaration.DECLARE_KEYWORD)
private val EXPORT_MODIFIERS = listOf(ModifierDeclaration.EXPORT_KEYWORD)
private val STATIC_MODIFIERS = listOf(ModifierDeclaration.STATIC_KEYWORD)

private fun getVariableDeclaration(name: String, type: ParameterValueDeclaration, modifiers: List<ModifierDeclaration>) = VariableDeclaration(
        name = name,
        type = type,
        modifiers = modifiers,
        initializer = null,
        uid = generateUID()
)

private fun getPropertyDeclaration(name: String, type: ParameterValueDeclaration, isStatic: Boolean) = PropertyDeclaration(
        name = name,
        initializer = null,
        type = type,
        typeParameters = emptyList(),
        optional = false,
        modifiers = if (isStatic) STATIC_MODIFIERS else emptyList()
)

private fun Constraint.toParameterDeclaration(name: String) = ParameterDeclaration(
        name = name,
        type = this.toType(),
        initializer = null,
        vararg = false,
        optional = false
)

private fun FunctionConstraint.toDeclarations(name: String, modifiers: List<ModifierDeclaration>) = overloads.map {
    FunctionDeclaration(
            name = name,
            parameters = it.parameterConstraints.map { (name, constraint) -> constraint.toParameterDeclaration(name) },
            type = it.returnConstraints.toType(),
            typeParameters = emptyList(),
            modifiers = modifiers,
            body = null,
            uid = generateUID()
    )
}

private fun FunctionConstraint.toConstructors() = overloads.map {
    ConstructorDeclaration(
            parameters = it.parameterConstraints.map { (name, constraint) -> constraint.toParameterDeclaration(name) },
            typeParameters = emptyList(),
            modifiers = emptyList(),
            body = null
    )
}

private fun FunctionConstraint.toCallSignatures() = overloads.map {
    CallSignatureDeclaration(
            parameters = it.parameterConstraints.map { (name, constraint) -> constraint.toParameterDeclaration(name) },
            type = it.returnConstraints.toType(),
            typeParameters = emptyList()
    )
}

private fun CallableConstraint.toCallSignature() = CallSignatureDeclaration(
        parameters = List(parameterCount) { NoTypeConstraint.toParameterDeclaration("") },
        type = returnConstraints.toType(),
        typeParameters = emptyList()
)

private fun FunctionConstraint.toMemberDeclarations(name: String, isStatic: Boolean) =
        this.toDeclarations(name, if (isStatic) STATIC_MODIFIERS else emptyList())

private fun Constraint.toMemberDeclarations(name: String, isStatic: Boolean = false) : List<MemberDeclaration> {
    return when (this) {
        is FunctionConstraint -> this.toMemberDeclarations(name, isStatic)
        else -> listOf(getPropertyDeclaration(name, this.toType(), isStatic))
    }
}

private fun ClassConstraint.toDeclaration(name: String, modifiers: List<ModifierDeclaration>) : ClassDeclaration {
    val members = mutableListOf<MemberDeclaration>()

    val constructorConstraint = constructorConstraint
    if (constructorConstraint is FunctionConstraint) {
        members.addAll(constructorConstraint.toConstructors())
    }

    val prototype = this[JavaScriptIdentifiers.PROTOTYPE]

    if (prototype is ObjectConstraint) {
        members.addAll(
                prototype.propertyNames.flatMap { memberName ->
                    prototype[memberName]?.toMemberDeclarations(name = memberName, isStatic = false) ?: emptyList()
                }
        )
    } else {
        raiseConcern("Class prototype is no object. Conversion might be erroneous!") {  }
    }

    members.addAll(
            propertyNames.flatMap { memberName ->
                //Don't output the prototype object
                if (memberName != JavaScriptIdentifiers.PROTOTYPE) {
                    this[memberName]?.toMemberDeclarations(name = memberName, isStatic = true) ?: emptyList()
                } else {
                    emptyList()
                }
            }
    )

    return ClassDeclaration(
            name = IdentifierEntity(name),
            members = members,
            typeParameters = emptyList(),
            parentEntities = emptyList(),
            modifiers = modifiers,
            uid = generateUID()
    )
}

private fun FunctionConstraint.toType() : ParameterValueDeclaration {
    return UnionTypeDeclaration(
            params = overloads.map {
                FunctionTypeDeclaration(
                        parameters = it.parameterConstraints.map { (name, constraint) -> constraint.toParameterDeclaration(name) },
                        type = it.returnConstraints.toType()
                )
            }
    )
}

private fun UnionTypeConstraint.toType() : ParameterValueDeclaration {
    return UnionTypeDeclaration(
            params = types.map { it.toType() }
    )
}

private fun ObjectConstraint.mapMembers() : List<MemberDeclaration> {
    val members = mutableListOf<MemberDeclaration>()

    callSignatureConstraints.forEach {
        when (it) {
            is FunctionConstraint -> members.addAll(it.toCallSignatures())
            is CallableConstraint -> members.add(it.toCallSignature())
        }
    }

    members.addAll(
            propertyNames.flatMap { memberName ->
                this[memberName]?.toMemberDeclarations(name = memberName) ?: emptyList()
            }
    )

    if (instantiatedClass is PropertyOwner) {
        val classPrototype = instantiatedClass[JavaScriptIdentifiers.PROTOTYPE]

        if (classPrototype is ObjectConstraint) {
            members.addAll(classPrototype.mapMembers())
        }
    }

    return members
}

private fun ObjectConstraint.toType() : ObjectLiteralDeclaration {
    return ObjectLiteralDeclaration(
            members = mapMembers(),
            uid = generateUID()
    )
}

private fun Constraint.toType() : ParameterValueDeclaration {
    return when (this) {
        is NumberTypeConstraint -> NUMBER_TYPE
        is BigIntTypeConstraint -> NUMBER_TYPE
        is BooleanTypeConstraint -> BOOLEAN_TYPE
        is StringTypeConstraint -> STRING_TYPE
        is VoidTypeConstraint -> VOID_TYPE
        is ThrowConstraint -> NOTHING_TYPE
        is UnionTypeConstraint -> this.toType()
        is ObjectConstraint -> this.toType()
        is FunctionConstraint -> this.toType()
        else -> ANY_NULLABLE_TYPE
    }
}

private fun Constraint.toDeclarations(name: String, exportModifiers: List<ModifierDeclaration>) : List<TopLevelDeclaration> {
    return when (this) {
        is ClassConstraint -> listOf(this.toDeclaration(name, exportModifiers))
        is FunctionConstraint -> this.toDeclarations(name, exportModifiers)
        is CompositeConstraint -> raiseConcern("Unexpected composited type for variable named '$name'. Should be resolved by this point!") { emptyList<TopLevelDeclaration>() }
        else -> listOf(getVariableDeclaration(name, this.toType(), exportModifiers))
    }
}

fun Constraint.toDeclarations(name: String) = toDeclarations(name, EXPORT_MODIFIERS)

fun Constraint.asDefaultToDeclarations(defaultExportName: String) : List<TopLevelDeclaration> {
    val declarations = this.toDeclarations(defaultExportName, DECLARE_MODIFIERS).toMutableList()

    val uidOwner = declarations.firstOrNull { it is WithUidDeclaration }

    if (uidOwner is WithUidDeclaration) {
        declarations.add(ExportAssignmentDeclaration(uidOwner.uid, true))
    }

    return declarations
}

