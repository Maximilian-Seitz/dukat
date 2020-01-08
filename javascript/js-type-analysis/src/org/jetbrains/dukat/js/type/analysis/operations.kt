package org.jetbrains.dukat.js.type.analysis

import org.jetbrains.dukat.js.type.constraint.Constraint
import org.jetbrains.dukat.js.type.constraint.composite.CompositeConstraint
import org.jetbrains.dukat.js.type.constraint.conditional.EqualsConstraint
import org.jetbrains.dukat.js.type.constraint.conditional.NegatedConstraint
import org.jetbrains.dukat.js.type.constraint.immutable.resolved.BooleanTypeConstraint
import org.jetbrains.dukat.js.type.constraint.immutable.resolved.NoTypeConstraint
import org.jetbrains.dukat.js.type.constraint.immutable.resolved.NumberTypeConstraint
import org.jetbrains.dukat.js.type.constraint.immutable.resolved.values.FalsyConstraint
import org.jetbrains.dukat.js.type.constraint.immutable.resolved.values.TruthyConstraint
import org.jetbrains.dukat.js.type.propertyOwner.PropertyOwner
import org.jetbrains.dukat.tsmodel.ExpressionDeclaration
import org.jetbrains.dukat.tsmodel.expression.BinaryExpressionDeclaration
import org.jetbrains.dukat.tsmodel.expression.UnaryExpressionDeclaration

fun calculateConstraintsFromBinaryOperation(owner: PropertyOwner, path: PathWalker, left: ExpressionDeclaration, operator: String, right: ExpressionDeclaration) : Constraint {
    return when (operator) {
        // Assignments
        "=" -> {
            val rightConstraints = right.calculateConstraints(owner, path)
            owner[left, path] = rightConstraints
            rightConstraints
        }
        "-=", "*=", "/=", "%=", "**=", "&=", "^=", "|=", "<<=", ">>=", ">>>=" -> {
            left.calculateConstraints(owner, path) += NumberTypeConstraint
            right.calculateConstraints(owner, path) += NumberTypeConstraint
            owner[left, path] = NumberTypeConstraint
            NumberTypeConstraint
        }

        // Non-assignments
        "||" -> {
            when (path.getNextDirection()) {
                PathWalker.Direction.Left -> {
                    left.calculateConstraints(owner, path).also {
                        it += TruthyConstraint
                    }
                    //right isn't being evaluated
                }
                PathWalker.Direction.Right -> {
                    left.calculateConstraints(owner, path) += FalsyConstraint
                    right.calculateConstraints(owner, path)
                }
            }
        }
        "&&" -> {
            when (path.getNextDirection()) {
                PathWalker.Direction.Left -> {
                    left.calculateConstraints(owner, path) += TruthyConstraint
                    right.calculateConstraints(owner, path)
                }
                PathWalker.Direction.Right -> {
                    left.calculateConstraints(owner, path).also {
                        it += FalsyConstraint
                    }
                    //right isn't being evaluated
                }
            }
        }
        "-", "*", "/", "**", "%", "++", "--" -> {
            left.calculateConstraints(owner, path) += NumberTypeConstraint
            right.calculateConstraints(owner, path) += NumberTypeConstraint
            NumberTypeConstraint
        }
        "&", "|", "^", "<<", ">>", ">>>" -> {
            left.calculateConstraints(owner, path)
            right.calculateConstraints(owner, path)
            NumberTypeConstraint
        }
        "==", "in", "instanceof" -> {
            left.calculateConstraints(owner, path)
            right.calculateConstraints(owner, path)
            BooleanTypeConstraint
        }
        "===" -> {
            EqualsConstraint(
                    left.calculateConstraints(owner, path),
                    right.calculateConstraints(owner, path)
            )
        }
        "!=" -> {
            NegatedConstraint(
                    calculateConstraintsFromBinaryOperation(owner, path, left, "==", right)
            )
        }
        "!==" -> {
            NegatedConstraint(
                    calculateConstraintsFromBinaryOperation(owner, path, left, "===", right)
            )
        }
        ">", "<", ">=", "<=" -> {
            left.calculateConstraints(owner, path) += NumberTypeConstraint
            right.calculateConstraints(owner, path) += NumberTypeConstraint
            BooleanTypeConstraint
        }
        else -> {
            left.calculateConstraints(owner, path)
            right.calculateConstraints(owner, path)
            CompositeConstraint(owner, NoTypeConstraint)
        }
    }
}

fun BinaryExpressionDeclaration.calculateConstraints(owner: PropertyOwner, path: PathWalker) : Constraint {
    return calculateConstraintsFromBinaryOperation(owner, path, left, operator, right)
}

fun UnaryExpressionDeclaration.calculateConstraints(owner: PropertyOwner, path: PathWalker) : Constraint {
    val operandConstraints = operand.calculateConstraints(owner, path)

    return when (operator) {
        "--", "++", "~" -> {
            operandConstraints += NumberTypeConstraint
            owner[operand, path] = NumberTypeConstraint
            NumberTypeConstraint
        }
        "-", "+" -> {
            operandConstraints += NumberTypeConstraint
            NumberTypeConstraint
        }
        "!" -> {
            NegatedConstraint(operandConstraints)
        }
        else -> {
            CompositeConstraint(owner, NoTypeConstraint)
        }
    }
}