package org.jetbrains.dukat.js.type.exportResolution

import org.jetbrains.dukat.js.identifiers.CommonJSIdentifiers
import org.jetbrains.dukat.js.type.constraint.properties.ObjectConstraint
import org.jetbrains.dukat.js.type.constraint.resolution.asDefaultToDeclarations
import org.jetbrains.dukat.js.type.propertyOwner.Scope
import org.jetbrains.dukat.panic.raiseConcern
import org.jetbrains.dukat.tsmodel.TopLevelDeclaration

class CommonJSContext : TypeAnalysisContext {
    private val basicContext = BasicJSContext()

    override fun getEnvironment(): Scope {
        val env = basicContext.getEnvironment()

        val moduleObject = ObjectConstraint(env)
        val exportsObject = ObjectConstraint(env)

        moduleObject[CommonJSIdentifiers.EXPORTS] = exportsObject
        env[CommonJSIdentifiers.MODULE] = moduleObject
        env[CommonJSIdentifiers.EXPORTS] = exportsObject

        return env
    }

    override fun getExportsFrom(environment: Scope, defaultExportName: String): List<TopLevelDeclaration> {
        val moduleObject = environment[CommonJSIdentifiers.MODULE]

        if (moduleObject is ObjectConstraint) {
            val exportsObject = moduleObject[CommonJSIdentifiers.EXPORTS]

            if (exportsObject != null) {
                return if (exportsObject is ObjectConstraint && exportsObject.callSignatureConstraints.isEmpty()) {
                    basicContext.getExportsFrom(
                            Scope(null).apply {
                                exportsObject.propertyNames.forEach {
                                    this[it] = exportsObject[it]!!
                                }
                            },
                            defaultExportName
                    )
                } else {
                    exportsObject.asDefaultToDeclarations(defaultExportName)
                }
            }
        }

        return raiseConcern("No exports found!") { emptyList() }
    }
}