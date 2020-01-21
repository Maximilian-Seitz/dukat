package org.jetbrains.dukat.tsmodel

interface ForIteratorStatementDeclaration : TopLevelDeclaration {
    val initializer: TopLevelDeclaration
    val expression: ExpressionDeclaration
    val statement: TopLevelDeclaration
}