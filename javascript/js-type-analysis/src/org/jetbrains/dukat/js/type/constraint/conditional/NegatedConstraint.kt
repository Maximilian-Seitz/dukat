package org.jetbrains.dukat.js.type.constraint.conditional

import org.jetbrains.dukat.js.type.constraint.Constraint
import org.jetbrains.dukat.js.type.constraint.immutable.resolved.BooleanTypeConstraint
import org.jetbrains.dukat.js.type.constraint.immutable.resolved.values.FalsyConstraint
import org.jetbrains.dukat.js.type.constraint.immutable.resolved.values.TruthyConstraint

class NegatedConstraint(private val condition: Constraint) : Constraint {

    override fun plusAssign(other: Constraint) {
        when (other) {
            is FalsyConstraint -> condition += TruthyConstraint
            is TruthyConstraint -> condition += FalsyConstraint
            else -> condition += other
        }
    }

    override fun resolve(resolveAsInput: Boolean): Constraint {
        condition.resolve(resolveAsInput)

        return BooleanTypeConstraint
    }
}
