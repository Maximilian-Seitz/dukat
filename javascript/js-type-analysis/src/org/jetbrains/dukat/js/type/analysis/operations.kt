package org.jetbrains.dukat.js.type.analysis

import org.jetbrains.dukat.js.type.constraint.Constraint
import org.jetbrains.dukat.js.type.constraint.composite.CompositeConstraint
import org.jetbrains.dukat.js.type.constraint.conditional.NegatedConstraint
import org.jetbrains.dukat.js.type.constraint.immutable.resolved.BooleanTypeConstraint
import org.jetbrains.dukat.js.type.constraint.immutable.resolved.NoTypeConstraint
import org.jetbrains.dukat.js.type.constraint.immutable.resolved.NumberTypeConstraint
import org.jetbrains.dukat.js.type.property_owner.PropertyOwner
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
        "&&", "||" -> {
            when (path.getNextDirection()) {
                PathWalker.Direction.First -> {
                    left.calculateConstraints(owner, path)
                    //right isn't being evaluated
                }
                PathWalker.Direction.Second -> {
                    left.calculateConstraints(owner, path)
                    right.calculateConstraints(owner, path)
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
        "==", "===", "in", "instanceof" -> {
            left.calculateConstraints(owner, path)
            right.calculateConstraints(owner, path)
            BooleanTypeConstraint
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