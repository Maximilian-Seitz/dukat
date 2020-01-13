@file:Suppress("RemoveEmptyParenthesesFromLambdaCall")
package org.jetbrains.dukat.js.type.inbuilt

import org.jetbrains.dukat.js.type.inbuilt.builder.jsClass

val regExpClass by lazy {
    jsClass {
        constructor("pattern" to string, "flags" to string)

        "dotAll" - boolean
        "flags" - string
        "global" - boolean
        "ignoreCase" - boolean
        "multiline" - boolean
        "source" - string
        "sticky" - boolean
        "unicode" - boolean
        "lastIndex" - number

        "compile"("pattern" to string, "flags" to string) { void }
        "exec"("str" to string) { any }
        "test"("str" to string) { boolean }
    }
}