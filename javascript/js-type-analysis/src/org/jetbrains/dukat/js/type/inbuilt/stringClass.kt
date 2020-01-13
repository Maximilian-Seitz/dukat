@file:Suppress("RemoveEmptyParenthesesFromLambdaCall")
package org.jetbrains.dukat.js.type.inbuilt

import org.jetbrains.dukat.js.type.inbuilt.builder.jsClass
import org.jetbrains.dukat.js.type.inbuilt.builder.jsObject

internal val stringClass by lazy {
    jsClass {
        constructor("thing" to any)

        "length" - number

        static {
            "fromCharCode"(*vararg { "num$it" to number }) { string }
            "fromCodePoint"(*vararg { "num$it" to number }) { string }
        }

        "anchor"("name" to string) { string }
        "big"() { string }
        "blink"() { string }
        "bold"() { string }
        "charAt"("index" to number) { string }
        "charCodeAt"("index" to number) { number }
        "codePointAt"("pos" to number) { number }
        "concat"(*vararg { "str$it" to string }) { string }
        "endsWith"("searchString" to string, "length" to number) { boolean }
        "fixed"() { string }
        "fontcolor"("color" to string) { string }
        "fontsize"("size" to number) { string }
        "includes"("searchString" to string, "position" to number) { boolean }
        "indexOf"("searchValue" to string, "fromIndex" to number) { number }
        "italics"() { string }
        "lastIndexOf"("searchValue" to string, "fromIndex" to number) { number }
        "link"("url" to string) { string }
        "localeCompare"(
                "compareString" to string,
                "locales" to string,
                "options" to jsObject {
                    "localeMatcher" - string
                    "usage" - string
                    "sensitivity" - string
                    "ignorePunctuation" - boolean
                    "numeric" - boolean
                    "caseFirst" - string
                }
        ) { any }
        "match"("regexp" to any) { any }
        "matchAll"("regexp" to any) { any }
        "normalize"("form" to string) { any }
        "padEnd"("targetLength" to number, "padString" to string) { string }
        "padStart"("targetLength" to number, "padString" to string) { string }
        "repeat"("count" to number) { string }
        "replace"("original" to any, "new" to any) { any }
        "search"("regexp" to any) { string }
        "slice"("beginIndex" to number, "endIndex" to number) { string }
        "small"() { string }
        "split"("separator" to any, "limit" to number) { any }
        "startsWith"("searchString" to string, "position" to number) { boolean }
        "strike"() { string }
        "sub"() { string }
        "substr"("start" to number, "length" to number) { string }
        "substring"("indexStart" to number, "indexEnd" to number) { string }
        "sup"() { string }
        "toLocaleLowerCase"("locale" to any) { string }
        "toLocaleUpperCase"("locale" to any) { string }
        "toLowerCase"() { string }
        "toString"() { string }
        "toUpperCase"() { string }
        "trim"() { string }
        "trimEnd"() { string }
        "trimStart"() { string }
        "valueOf"() { string }
    }
}