@file:Suppress("RemoveEmptyParenthesesFromLambdaCall")
package org.jetbrains.dukat.js.type.inbuilt

import org.jetbrains.dukat.js.type.inbuilt.builder.jsClass

internal val arrayClass by jsClass {
    "length" isType number

    static {
        "isArray"("value" to any) { boolean }
        "from"("arrayLike" to any, "mapFn" to any, "thisArg" to any) { thisType }
        "of"() { thisType }
    }

    "concat"() { thisType }
    "copyWithin"("target" to number, "start" to number, "end" to number) { thisType }
    "fill"("value" to any, "start" to number, "end" to number) { any }
    "find"() { any }
    "findIndex"() { number }
    "lastIndexOf"() { number }
    "pop"() { any }
    "push"() { number }
    "reverse"() { any }
    "shift"() { any }
    "unshift"() { any }
    "slice"() { thisType }
    "sort"() { thisType }
    "splice"() { thisType }
    "includes"("element" to any, "start" to number) { boolean }
    "indexOf"() { number }
    "join"() { thisType }
    "keys"() { thisType }
    "entries"() { any }
    "values"() { any }
    "forEach"() { any }
    "filter"() { any }
    "flat"() { any }
    "flatMap"() { any }
    "map"() { any }
    "every"() { any }
    "some"() { any }
    "reduce"() { any }
    "reduceRight"() { any }
    "toLocaleString"() { string }
    "toString"() { string }
}