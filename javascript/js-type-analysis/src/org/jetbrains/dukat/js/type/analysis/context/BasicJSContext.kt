package org.jetbrains.dukat.js.type.analysis.context

import org.jetbrains.dukat.js.type.constraint.resolution.toDeclarations
import org.jetbrains.dukat.js.type.inbuilt.arrayClass
import org.jetbrains.dukat.js.type.inbuilt.jsonObject
import org.jetbrains.dukat.js.type.inbuilt.mathObject
import org.jetbrains.dukat.js.type.inbuilt.regExpClass
import org.jetbrains.dukat.js.type.propertyOwner.Scope
import org.jetbrains.dukat.tsmodel.TopLevelDeclaration

class BasicJSContext : TypeAnalysisContext {
    override fun getEnvironment(): Scope {
        val env = Scope(null)

        // build-in classes
        env["Array"] = arrayClass
        env["RegExp"] = regExpClass

        // build-in objects
        env["Math"] = mathObject
        env["JSON"] = jsonObject

        return env
    }

    override fun getExportsFrom(environment: Scope, defaultExportName: String) : List<TopLevelDeclaration> {
        return environment.propertyNames.flatMap { propertyName ->
            val resolvedConstraint = environment[propertyName]
            resolvedConstraint.toDeclarations(propertyName)
        }
    }
}
