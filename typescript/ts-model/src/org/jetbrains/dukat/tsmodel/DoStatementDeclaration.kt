package org.jetbrains.dukat.tsmodel

data class DoStatementDeclaration(
        val condition: ExpressionDeclaration,
        val statement: TopLevelDeclaration
) : TopLevelDeclaration
