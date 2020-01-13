@file:Suppress("RemoveEmptyParenthesesFromLambdaCall")
package org.jetbrains.dukat.js.type.analysis.context

import org.jetbrains.dukat.js.type.constraint.resolution.toDeclarations
import org.jetbrains.dukat.js.type.inbuilt.arrayClass
import org.jetbrains.dukat.js.type.inbuilt.builder.jsEnvironment
import org.jetbrains.dukat.js.type.inbuilt.jsonObject
import org.jetbrains.dukat.js.type.inbuilt.mathObject
import org.jetbrains.dukat.js.type.inbuilt.objectClass
import org.jetbrains.dukat.js.type.inbuilt.regExpClass
import org.jetbrains.dukat.js.type.propertyOwner.Scope
import org.jetbrains.dukat.tsmodel.TopLevelDeclaration

class BasicJSContext : TypeAnalysisContext {
    override fun getEnvironment(): Scope {
        return jsEnvironment {
            // build-in classes
            "Object" - objectClass
            "Array" - arrayClass
            "RegExp" - regExpClass

            // build-in objects
            "Math" - mathObject
            "JSON" - jsonObject

            // built-in functions
            "eval"("string" to string) { any }
            "isFinite"("testValue" to number) { boolean }
            "isNaN"("value" to number) { boolean }
            "parseFloat"("string" to string) { number }
            "parseInt"("string" to string, "radix" to number) { number }
            "decodeURI"("encodedURI" to string) { string }
            "decodeURIComponent"("encodedURI" to string) { string }
            "encodeURI"("URI" to string) { string }
            "encodeURIComponent"("str" to string) { string }
            "escape"("str" to string) { string }
            "unescape"("str" to string) { string }
        }
    }

    override fun getExportsFrom(environment: Scope, defaultExportName: String) : List<TopLevelDeclaration> {
        return environment.propertyNames.flatMap { propertyName ->
            val resolvedConstraint = environment[propertyName]
            resolvedConstraint.toDeclarations(propertyName)
        }
    }
}
