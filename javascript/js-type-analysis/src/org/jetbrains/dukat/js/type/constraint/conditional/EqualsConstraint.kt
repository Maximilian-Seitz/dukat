package org.jetbrains.dukat.js.type.constraint.conditional

import org.jetbrains.dukat.js.type.constraint.Constraint
import org.jetbrains.dukat.js.type.constraint.immutable.resolved.BooleanTypeConstraint
import org.jetbrains.dukat.js.type.constraint.immutable.resolved.values.TruthyConstraint

class EqualsConstraint(private val comparedElements: Pair<Constraint, Constraint>) : Constraint {
    constructor(left: Constraint, right: Constraint) : this(left to right)

    override fun plusAssign(other: Constraint) {
        if (other == TruthyConstraint) {
            comparedElements.first += comparedElements.second
            comparedElements.second += comparedElements.first
        }
    }

    override fun resolve(resolveAsInput: Boolean): Constraint {
        comparedElements.first.resolve(resolveAsInput)
        comparedElements.second.resolve(resolveAsInput)

        return BooleanTypeConstraint
    }
}
