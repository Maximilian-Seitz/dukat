@file:Suppress("RemoveEmptyParenthesesFromLambdaCall")
package org.jetbrains.dukat.js.type.inbuilt

import org.jetbrains.dukat.js.type.inbuilt.builder.jsFunction
import org.jetbrains.dukat.js.type.inbuilt.builder.jsObject

internal val jsonObject by lazy {
    jsObject {
        "parse"(
                "text" to string,
                "reviver" to jsFunction("key" to string, "value" to string) { any }
        ) { string }

        "stringify"(
                "value" to any,
                "replacer" to any,
                "space" to any
        ) { any }
    }
}