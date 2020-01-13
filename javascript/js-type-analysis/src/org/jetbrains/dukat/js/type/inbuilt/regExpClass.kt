@file:Suppress("RemoveEmptyParenthesesFromLambdaCall")
package org.jetbrains.dukat.js.type.inbuilt

import org.jetbrains.dukat.js.type.inbuilt.builder.jsClass

val regExpClass by jsClass {
    constructor("pattern" to string, "flags" to string)

    "dotAll" isType boolean
    "flags" isType string
    "global" isType boolean
    "ignoreCase" isType boolean
    "multiline" isType boolean
    "source" isType string
    "sticky" isType boolean
    "unicode" isType boolean
    "lastIndex" isType number

    "compile"("pattern" to string, "flags" to string) { void }
    "exec"("str" to string) { any }
    "test"("str" to string) { boolean }
}