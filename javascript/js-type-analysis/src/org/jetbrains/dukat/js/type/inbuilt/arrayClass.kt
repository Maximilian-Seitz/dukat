@file:Suppress("RemoveEmptyParenthesesFromLambdaCall")
package org.jetbrains.dukat.js.type.inbuilt

import org.jetbrains.dukat.js.type.inbuilt.builder.jsClass
import org.jetbrains.dukat.js.type.inbuilt.builder.jsFunction

internal val arrayClass by lazy {
    jsClass {
        val callbackArguments = arrayOf("element" to any, "index" to number, "array" to thisType)
        val itemChecker = jsFunction(*callbackArguments) { boolean }
        val itemConsumer = jsFunction(*callbackArguments) { void }
        val itemTransformer = jsFunction(*callbackArguments) { any }
        val reducer = jsFunction("accumulator" to any, "currentValue" to any, "index" to number, "array" to thisType) { any }
        val comparator = jsFunction("firstEl" to any, "secondEl" to any) { number }

        constructor(*vararg { "value$it" to any })

        "length" - number

        static {
            "from"("arrayLike" to any, "mapFn" to any, "thisArg" to any) { thisType }
            "isArray"("value" to any) { boolean }
            "of"(*vararg { "element$it" to any }) { thisType }
        }

        "concat"(*vararg { "value$it" to any }) { thisType }
        "copyWithin"("target" to number, "start" to number, "end" to number) { thisType }
        "entries"() { any }
        "every"(
                "callback" to itemChecker,
                "thisArg" to any
        ) { boolean }
        "fill"("value" to any, "start" to number, "end" to number) { any }
        "filter"(
                "callback" to itemChecker,
                "thisArg" to any
        ) { thisType }
        "find"(
                "callback" to itemChecker,
                "thisArg" to any
        ) { any }
        "findIndex"(
                "callback" to itemChecker,
                "thisArg" to any
        ) { number }
        "flat"("depth" to number) { thisType }
        "flatMap"(
                "callback" to itemTransformer,
                "thisArg" to any
        ) { thisType }
        "forEach"(
                "callback" to itemConsumer,
                "thisArg" to any
        ) { void }
        "includes"("valueToFind" to any, "fromIndex" to number) { boolean }
        "indexOf"("searchElement" to any, "fromIndex" to number) { number }
        "join"("separator" to string) { string }
        "keys"() { any }
        "lastIndexOf"("searchElement" to any, "fromIndex" to number) { number }
        "map"(
                "callback" to itemTransformer,
                "thisArg" to any
        ) { thisType }
        "pop"() { any }
        "push"(*vararg { "element$it" to any }) { number }
        "reduce"("callback" to reducer, "initialValue" to any) { any }
        "reduceRight"("callback" to reducer, "initialValue" to any) { any }
        "reverse"() { thisType }
        "shift"() { any }
        "slice"("begin" to number, "end" to number) { thisType }
        "some"("callback" to itemChecker,
                "thisArg" to any
        ) { boolean }
        "sort"("compareFunction" to comparator) { thisType }
        "splice"("start" to number, "deleteCount" to number, *vararg { "item$it" to any }) { thisType }
        "toLocaleString"("locales" to any, "options" to any) { string }
        "toString"() { string }
        "unshift"(*vararg { "element$it" to any }) { number }
        "values"() { any }
    }
}