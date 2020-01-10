@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS", "EXTERNAL_DELEGATION")

import kotlin.js.*
import kotlin.js.Json
import org.khronos.webgl.*
import org.w3c.dom.*
import org.w3c.dom.events.*
import org.w3c.dom.parsing.*
import org.w3c.dom.svg.*
import org.w3c.dom.url.*
import org.w3c.fetch.*
import org.w3c.files.*
import org.w3c.notifications.*
import org.w3c.performance.*
import org.w3c.workers.*
import org.w3c.xhr.*

external object Math {
    var E: Number
    var LN10: Number
    var LN2: Number
    var LOG10E: Number
    var LOG2E: Number
    var PI: Number
    var SQRT1_2: Number
    var SQRT2: Number
    fun abs(x: Number): Number
    fun acos(x: Number): Number
    fun acosh(x: Number): Number
    fun asin(x: Number): Number
    fun asinh(x: Number): Number
    fun atan(x: Number): Number
    fun atan2(y: Number, x: Number): Number
    fun atanh(x: Number): Number
    fun cbrt(x: Number): Number
    fun ceil(x: Number): Number
    fun clz32(x: Number): Number
    fun cos(x: Number): Number
    fun cosh(x: Number): Number
    fun exp(x: Number): Number
    fun expm1(x: Number): Number
    fun floor(x: Number): Number
    fun fround(doubleFloat: Number): Number
    fun hypot(value1: Number, value2: Number, value3: Number, value4: Number, value5: Number, value6: Number, value7: Number, value8: Number, value9: Number, value10: Number, value11: Number, value12: Number, value13: Number, value14: Number, value15: Number, value16: Number, value17: Number, value18: Number, value19: Number, value20: Number): Number
    fun imul(a: Number, b: Number): Number
    fun log(x: Number): Number
    fun log10(argA: Number, argB: Number, argC: Number): Number
    fun log1p(argA: Number, argB: Number, argC: Number): Number
    fun log2(argA: Number, argB: Number, argC: Number): Number
    fun max(value1: Number, value2: Number, value3: Number, value4: Number, value5: Number, value6: Number, value7: Number, value8: Number, value9: Number, value10: Number, value11: Number, value12: Number, value13: Number, value14: Number, value15: Number, value16: Number, value17: Number, value18: Number, value19: Number, value20: Number): Number
    fun min(value1: Number, value2: Number, value3: Number, value4: Number, value5: Number, value6: Number, value7: Number, value8: Number, value9: Number, value10: Number, value11: Number, value12: Number, value13: Number, value14: Number, value15: Number, value16: Number, value17: Number, value18: Number, value19: Number, value20: Number): Number
    fun pow(base: Number, exponent: Number): Number
    fun random(): Number
    fun round(x: Number): Number
    fun sign(x: Number): Number
    fun sin(x: Number): Number
    fun sinh(x: Number): Number
    fun sqrt(x: Number): Number
    fun tan(x: Number): Number
    fun tanh(x: Number): Number
    fun trunc(x: Number): Number
}