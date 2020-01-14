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

external object arr {
    var `0`: String
    var `1`: String
    var `2`: String
    var length: Number
    fun concat(value1: Any?, value2: Any?, value3: Any?, value4: Any?, value5: Any?, value6: Any?, value7: Any?, value8: Any?, value9: Any?, value10: Any?, value11: Any?, value12: Any?, value13: Any?, value14: Any?, value15: Any?, value16: Any?, value17: Any?, value18: Any?, value19: Any?, value20: Any?): Any?
    fun copyWithin(target: Number, start: Number, end: Number): Any?
    fun entries(): Any?
    fun every(callback: (element: Any? /* = null */, index: Number, array: Any? /* = null */) -> Boolean, thisArg: Any?): Boolean
    fun fill(value: Any?, start: Number, end: Number): Any?
    fun filter(callback: (element: Any? /* = null */, index: Number, array: Any? /* = null */) -> Boolean, thisArg: Any?): Any?
    fun find(callback: (element: Any? /* = null */, index: Number, array: Any? /* = null */) -> Boolean, thisArg: Any?): Any?
    fun findIndex(callback: (element: Any? /* = null */, index: Number, array: Any? /* = null */) -> Boolean, thisArg: Any?): Number
    fun flat(depth: Number): Any?
    fun flatMap(callback: (element: Any? /* = null */, index: Number, array: Any? /* = null */) -> Any?, thisArg: Any?): Any?
    fun forEach(callback: (element: Any? /* = null */, index: Number, array: Any? /* = null */) -> Unit, thisArg: Any?)
    fun includes(valueToFind: Any?, fromIndex: Number): Boolean
    fun indexOf(searchElement: Any?, fromIndex: Number): Number
    fun join(separator: String): String
    fun keys(): Any?
    fun lastIndexOf(searchElement: Any?, fromIndex: Number): Number
    fun map(callback: (element: Any? /* = null */, index: Number, array: Any? /* = null */) -> Any?, thisArg: Any?): Any?
    fun pop(): Any?
    fun push(element1: Any?, element2: Any?, element3: Any?, element4: Any?, element5: Any?, element6: Any?, element7: Any?, element8: Any?, element9: Any?, element10: Any?, element11: Any?, element12: Any?, element13: Any?, element14: Any?, element15: Any?, element16: Any?, element17: Any?, element18: Any?, element19: Any?, element20: Any?): Number
    fun reduce(callback: (accumulator: Any? /* = null */, currentValue: Any? /* = null */, index: Number, array: Any? /* = null */) -> Any?, initialValue: Any?): Any?
    fun reduceRight(callback: (accumulator: Any? /* = null */, currentValue: Any? /* = null */, index: Number, array: Any? /* = null */) -> Any?, initialValue: Any?): Any?
    fun reverse(): Any?
    fun shift(): Any?
    fun slice(begin: Number, end: Number): Any?
    fun some(callback: (element: Any? /* = null */, index: Number, array: Any? /* = null */) -> Boolean, thisArg: Any?): Boolean
    fun sort(compareFunction: (firstEl: Any? /* = null */, secondEl: Any? /* = null */) -> Number): Any?
    fun splice(start: Number, deleteCount: Number, item1: Any?, item2: Any?, item3: Any?, item4: Any?, item5: Any?, item6: Any?, item7: Any?, item8: Any?, item9: Any?, item10: Any?, item11: Any?, item12: Any?, item13: Any?, item14: Any?, item15: Any?, item16: Any?, item17: Any?, item18: Any?, item19: Any?, item20: Any?): Any?
    fun toLocaleString(locales: Any?, options: Any?): String
    fun toString(): String
    fun unshift(element1: Any?, element2: Any?, element3: Any?, element4: Any?, element5: Any?, element6: Any?, element7: Any?, element8: Any?, element9: Any?, element10: Any?, element11: Any?, element12: Any?, element13: Any?, element14: Any?, element15: Any?, element16: Any?, element17: Any?, element18: Any?, element19: Any?, element20: Any?): Number
    fun values(): Any?
}