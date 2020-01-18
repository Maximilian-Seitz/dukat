package org.jetbrains.dukat.tsmodel

data class ForStatementDeclaration(
        val initializer: TopLevelDeclaration?,
        val condition: ExpressionDeclaration?,
        val incrementor: ExpressionDeclaration?,
        val statement: TopLevelDeclaration
) : TopLevelDeclaration
