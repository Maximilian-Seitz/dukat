package org.jetbrains.dukat.js.type.analysis

import org.jetbrains.dukat.js.type.analysis.flowControl.BreakInstruction
import org.jetbrains.dukat.js.type.analysis.flowControl.ContinueInstruction
import org.jetbrains.dukat.js.type.analysis.flowControl.FlowInstruction
import org.jetbrains.dukat.js.type.analysis.flowControl.ReturnInstruction
import org.jetbrains.dukat.js.type.analysis.flowControl.ThrowInstruction
import org.jetbrains.dukat.js.type.constraint.immutable.resolved.NoTypeConstraint
import org.jetbrains.dukat.js.type.propertyOwner.PropertyOwner
import org.jetbrains.dukat.panic.raiseConcern
import org.jetbrains.dukat.tsmodel.BlockDeclaration
import org.jetbrains.dukat.tsmodel.BreakStatementDeclaration
import org.jetbrains.dukat.tsmodel.ClassDeclaration
import org.jetbrains.dukat.tsmodel.ContinueStatementDeclaration
import org.jetbrains.dukat.tsmodel.ExpressionStatementDeclaration
import org.jetbrains.dukat.tsmodel.ForStatementDeclaration
import org.jetbrains.dukat.tsmodel.FunctionDeclaration
import org.jetbrains.dukat.tsmodel.IfStatementDeclaration
import org.jetbrains.dukat.tsmodel.ModuleDeclaration
import org.jetbrains.dukat.tsmodel.ReturnStatementDeclaration
import org.jetbrains.dukat.tsmodel.SwitchStatementDeclaration
import org.jetbrains.dukat.tsmodel.ThrowStatementDeclaration
import org.jetbrains.dukat.tsmodel.TopLevelDeclaration
import org.jetbrains.dukat.tsmodel.VariableDeclaration
import org.jetbrains.dukat.tsmodel.WhileStatementDeclaration

fun BlockDeclaration.calculateConstraints(owner: PropertyOwner, path: PathWalker) : FlowInstruction? {
    return statements.calculateConstraints(owner, path)
}

fun VariableDeclaration.addTo(owner: PropertyOwner, path: PathWalker) {
    owner[name] = initializer?.calculateConstraints(owner, path) ?: NoTypeConstraint
}

fun IfStatementDeclaration.calculateConstraints(owner: PropertyOwner, path: PathWalker) : FlowInstruction? {
    condition.calculateConstraints(owner, path)

    return when (path.getNextDirection()) {
        PathWalker.Direction.Left -> thenStatement.calculateConstraints(owner, path)
        PathWalker.Direction.Right -> elseStatement?.calculateConstraints(owner, path)
    }
}

fun filterBreakAndContinue(flowInstruction: FlowInstruction?) = when (flowInstruction) {
    is BreakInstruction, ContinueInstruction -> null
    else -> flowInstruction
}

fun WhileStatementDeclaration.calculateConstraints(owner: PropertyOwner, path: PathWalker) : FlowInstruction? {
    condition.calculateConstraints(owner, path)

    val flowInstruction = when (path.getNextDirection()) {
        PathWalker.Direction.Left -> statement.calculateConstraints(owner, path)
        PathWalker.Direction.Right -> null
    }

    return filterBreakAndContinue(flowInstruction)
}

fun ForStatementDeclaration.calculateConstraints(owner: PropertyOwner, path: PathWalker) : FlowInstruction? {
    initializer?.calculateConstraints(owner, path) //The initializer can't contain flow control

    condition?.calculateConstraints(owner, path)

    val flowInstruction = when (path.getNextDirection()) {
        PathWalker.Direction.Left -> {
            val flowInstruction = statement.calculateConstraints(owner, path)
            incrementor?.calculateConstraints(owner, path)
            flowInstruction
        }
        PathWalker.Direction.Right -> null
    }

    return if (condition == null && flowInstruction == null) {
        // For loop without condition loops until it terminates otherwise.
        // Without FlowInstruction that doesn't happen, so this is an infinite loop,
        // which returns "Nothing", as if an exception was thrown
        ThrowInstruction
    } else {
        filterBreakAndContinue(flowInstruction)
    }
}

//TODO support for-in and for-of statements

fun SwitchStatementDeclaration.calculateConstraints(owner: PropertyOwner, path: PathWalker) : FlowInstruction? {
    expression.calculateConstraints(owner, path)

    var wasCaseSelected = false
    cases.forEach { case ->
        wasCaseSelected = if (case.expression == null || wasCaseSelected) {
            //case.expression == null   => default case
            //wasCaseSelected           => a previous case has already been chosen
            true
        } else {
            case.expression?.calculateConstraints(owner, path)

            path.getNextDirection() == PathWalker.Direction.Left
        }

        if (wasCaseSelected) {
            val flowInstruction = case.statement.calculateConstraints(owner, path)

            if (flowInstruction != null) {
                return filterBreakAndContinue(flowInstruction)
            }
        }
    }

    return null
}

fun ExpressionStatementDeclaration.calculateConstraints(owner: PropertyOwner, path: PathWalker) {
    expression.calculateConstraints(owner, path)
}

fun ReturnStatementDeclaration.calculateConstraints(owner: PropertyOwner, path: PathWalker) : FlowInstruction {
    return ReturnInstruction(expression?.calculateConstraints(owner, path))
}

fun ThrowStatementDeclaration.calculateConstraints(owner: PropertyOwner, path: PathWalker) : FlowInstruction {
    expression?.calculateConstraints(owner, path)
    return ThrowInstruction
}

fun TopLevelDeclaration.calculateConstraints(owner: PropertyOwner, path: PathWalker) : FlowInstruction? {
    when (this) {
        is FunctionDeclaration -> this.addTo(owner)
        is ClassDeclaration -> this.addTo(owner, path)
        is VariableDeclaration -> this.addTo(owner, path)
        is IfStatementDeclaration -> return this.calculateConstraints(owner, path)
        is WhileStatementDeclaration -> return this.calculateConstraints(owner, path)
        is ForStatementDeclaration -> return this.calculateConstraints(owner, path)
        is SwitchStatementDeclaration -> return this.calculateConstraints(owner, path)
        is ExpressionStatementDeclaration -> this.calculateConstraints(owner, path)
        is BlockDeclaration -> return this.calculateConstraints(owner, path)
        is ReturnStatementDeclaration -> return this.calculateConstraints(owner, path)
        is ThrowStatementDeclaration -> return this.calculateConstraints(owner, path)
        is BreakStatementDeclaration -> return BreakInstruction
        is ContinueStatementDeclaration -> return ContinueInstruction
        else -> raiseConcern("Unexpected top level entity type <${this::class}>") {  }
    }

    return null
}

fun List<TopLevelDeclaration>.calculateConstraints(owner: PropertyOwner, path: PathWalker) : FlowInstruction? {
    this.forEach { statement ->
        val statementFlowInstruction = statement.calculateConstraints(owner, path)

        if(statementFlowInstruction != null) {
            return statementFlowInstruction
        }
    }

    return null
}

fun ModuleDeclaration.calculateConstraints(owner: PropertyOwner, path: PathWalker) = declarations.calculateConstraints(owner, path)
