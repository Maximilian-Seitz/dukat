package org.jetbrains.dukat.tsmodel

data class ForInStatementDeclaration(
        val initializer: TopLevelDeclaration,
        val expression: ExpressionDeclaration,
        val statement: TopLevelDeclaration
) : TopLevelDeclaration
