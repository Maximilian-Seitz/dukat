@file:Suppress("RemoveEmptyParenthesesFromLambdaCall")
package org.jetbrains.dukat.js.type.inbuilt

import org.jetbrains.dukat.js.type.inbuilt.builder.jsObject

internal val mathObject by lazy {
    jsObject {
        "E" - number
        "LN10" - number
        "LN2" - number
        "LOG10E" - number
        "LOG2E" - number
        "PI" - number
        "SQRT1_2" - number
        "SQRT2" - number

        "abs"("x" to number) { number }
        "acos"("x" to number) { number }
        "acosh"("x" to number) { number }
        "asin"("x" to number) { number }
        "asinh"("x" to number) { number }
        "atan"("x" to number) { number }
        "atan2"("y" to number, "x" to number) { number }
        "atanh"("x" to number) { number }
        "cbrt"("x" to number) { number }
        "ceil"("x" to number) { number }
        "clz32"("x" to number) { number }
        "cos"("x" to number) { number }
        "cosh"("x" to number) { number }
        "exp"("x" to number) { number }
        "expm1"("x" to number) { number }
        "floor"("x" to number) { number }
        "fround"("doubleFloat" to number) { number }
        "hypot"(*vararg { "value$it" to number }) { number }
        "imul"("a" to number, "b" to number) { number }
        "log"("x" to number) { number }
        "log10"("argA" to number, "argB" to number, "argC" to number) { number }
        "log1p"("argA" to number, "argB" to number, "argC" to number) { number }
        "log2"("argA" to number, "argB" to number, "argC" to number) { number }
        "max"(*vararg { "value$it" to number }) { number }
        "min"(*vararg { "value$it" to number }) { number }
        "pow"("base" to number, "exponent" to number) { number }
        "random"() { number }
        "round"("x" to number) { number }
        "sign"("x" to number) { number }
        "sin"("x" to number) { number }
        "sinh"("x" to number) { number }
        "sqrt"("x" to number) { number }
        "tan"("x" to number) { number }
        "tanh"("x" to number) { number }
        "trunc"("x" to number) { number }
    }
}