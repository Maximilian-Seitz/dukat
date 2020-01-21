package org.jetbrains.dukat.tsmodel

data class ForOfStatementDeclaration(
        val hasAwaitModifier: Boolean,
        override val initializer: TopLevelDeclaration,
        override val expression: ExpressionDeclaration,
        override val statement: TopLevelDeclaration
) : ForIteratorStatementDeclaration
