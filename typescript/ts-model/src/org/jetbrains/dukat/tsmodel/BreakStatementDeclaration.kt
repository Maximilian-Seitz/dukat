package org.jetbrains.dukat.tsmodel

import org.jetbrains.dukat.astCommon.IdentifierEntity

data class BreakStatementDeclaration(
        val label: IdentifierEntity?
) : TopLevelDeclaration
