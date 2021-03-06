package org.jetbrains.dukat.tsmodel.types

import org.jetbrains.dukat.astCommon.NameEntity

data class TypeParamReferenceDeclaration(
        val value: NameEntity,
        override var nullable: Boolean = false,
        override var meta: ParameterValueDeclaration? = null
) : ParameterValueDeclaration
