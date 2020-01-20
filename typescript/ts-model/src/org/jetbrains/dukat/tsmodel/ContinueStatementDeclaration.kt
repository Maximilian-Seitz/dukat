package org.jetbrains.dukat.tsmodel

import org.jetbrains.dukat.astCommon.IdentifierEntity

data class ContinueStatementDeclaration(
        val label: IdentifierEntity?
) : TopLevelDeclaration
