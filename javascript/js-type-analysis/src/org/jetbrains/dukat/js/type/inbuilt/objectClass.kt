@file:Suppress("RemoveEmptyParenthesesFromLambdaCall")
package org.jetbrains.dukat.js.type.inbuilt

import org.jetbrains.dukat.js.type.inbuilt.builder.jsClass

val objectClass by lazy {
    jsClass {
        static {
            "assign"("target" to thisType, *vararg { "source$it" to thisType }) { thisType }
            "create"("proto" to thisType, "propertiesObject" to thisType) { thisType }
            "defineProperties"("obj" to thisType, "props" to thisType) { thisType }
            "defineProperty"("obj" to thisType, "prop" to string, "descriptor" to thisType) { thisType }
            "entries"("obj" to thisType) { any }
            "freeze"("obj" to thisType) { thisType }
            "fromEntries"("iterable" to any) { thisType }
            "getOwnPropertyDescriptor"("obj" to thisType, "prop" to string) { thisType }
            "getOwnPropertyDescriptors"("obj" to thisType) { thisType }
            "getOwnPropertyNames"("obj" to thisType) { any }
            "getOwnPropertySymbols"("obj" to thisType) { any }
            "getPrototypeOf"("obj" to thisType) { any }
            "is"("value1" to thisType, "value2" to thisType) { boolean }
            "isExtensible"("obj" to thisType) { boolean }
            "isFrozen"("obj" to thisType) { boolean }
            "isSealed"("obj" to thisType) { boolean }
            "keys"("obj" to thisType) { any }
            "preventExtensions"("obj" to thisType) { thisType }
            "seal"("obj" to thisType) { thisType }
            "setPrototypeOf"("obj" to thisType, "prototype" to thisType) { thisType }
            "values"("obj" to thisType) { any }
        }

        "hasOwnProperty"("prop" to string) { boolean }
        "isPrototypeOf"("object" to thisType) { boolean }
        "propertyIsEnumerable"("prop" to string) { boolean }
        "toLocaleString"() { string }
        "toString"() { string }
        "valueOf"() { any }
    }
}