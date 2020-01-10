package org.jetbrains.dukat.js.type.type

import org.jetbrains.dukat.astCommon.IdentifierEntity
import org.jetbrains.dukat.js.identifiers.JavaScriptIdentifiers
import org.jetbrains.dukat.tsmodel.types.TypeDeclaration

val VOID_TYPE = TypeDeclaration(
        value = IdentifierEntity("Unit"),
        params = emptyList(),
        nullable = false
)

val NOTHING_TYPE = TypeDeclaration(
        value = IdentifierEntity("Nothing"),
        params = emptyList(),
        nullable = false
)

val ANY_NULLABLE_TYPE = TypeDeclaration(
        value = IdentifierEntity(JavaScriptIdentifiers.ANY_TYPE),
        params = emptyList(),
        nullable = true
)

val NUMBER_TYPE = TypeDeclaration(
        value = IdentifierEntity(JavaScriptIdentifiers.NUMBER_TYPE),
        params = emptyList(),
        nullable = false
)

val BOOLEAN_TYPE = TypeDeclaration(
        value = IdentifierEntity(JavaScriptIdentifiers.BOOLEAN_TYPE),
        params = emptyList(),
        nullable = false
)

val STRING_TYPE = TypeDeclaration(
        value = IdentifierEntity(JavaScriptIdentifiers.STRING_TYPE),
        params = emptyList(),
        nullable = false
)