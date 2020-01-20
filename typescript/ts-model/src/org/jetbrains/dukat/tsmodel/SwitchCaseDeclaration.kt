package org.jetbrains.dukat.tsmodel

import org.jetbrains.dukat.astCommon.Entity

data class SwitchCaseDeclaration(
        val expression: ExpressionDeclaration?,
        val statement: TopLevelDeclaration
) : Entity
