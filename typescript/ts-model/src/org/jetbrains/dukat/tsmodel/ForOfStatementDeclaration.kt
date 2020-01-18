package org.jetbrains.dukat.tsmodel

data class ForOfStatementDeclaration(
        val hasAwaitModifier: Boolean,
        val initializer: TopLevelDeclaration,
        val expression: ExpressionDeclaration,
        val statement: TopLevelDeclaration
) : TopLevelDeclaration
