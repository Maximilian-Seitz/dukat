package org.jetbrains.dukat.js.type.analysis.flowControl

import org.jetbrains.dukat.js.type.constraint.Constraint

data class ReturnInstruction(
        val returnConstraint: Constraint?
) : FlowInstruction