package org.jetbrains.dukat.tsmodel

data class ForInStatementDeclaration(
        override val initializer: TopLevelDeclaration,
        override val expression: ExpressionDeclaration,
        override val statement: TopLevelDeclaration
) : ForIteratorStatementDeclaration
