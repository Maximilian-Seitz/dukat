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

external open class Array(value1: Any?, value2: Any?, value3: Any?, value4: Any?, value5: Any?, value6: Any?, value7: Any?, value8: Any?, value9: Any?, value10: Any?, value11: Any?, value12: Any?, value13: Any?, value14: Any?, value15: Any?, value16: Any?, value17: Any?, value18: Any?, value19: Any?, value20: Any?) {
    open var length: Number
    open fun concat(value1: Any?, value2: Any?, value3: Any?, value4: Any?, value5: Any?, value6: Any?, value7: Any?, value8: Any?, value9: Any?, value10: Any?, value11: Any?, value12: Any?, value13: Any?, value14: Any?, value15: Any?, value16: Any?, value17: Any?, value18: Any?, value19: Any?, value20: Any?): Any?
    open fun copyWithin(target: Number, start: Number, end: Number): Any?
    open fun entries(): Any?
    open fun every(callback: (element: Any? /* = null */, index: Number, array: Any? /* = null */) -> Boolean, thisArg: Any?): Boolean
    open fun fill(value: Any?, start: Number, end: Number): Any?
    open fun filter(callback: (element: Any? /* = null */, index: Number, array: Any? /* = null */) -> Boolean, thisArg: Any?): Any?
    open fun find(callback: (element: Any? /* = null */, index: Number, array: Any? /* = null */) -> Boolean, thisArg: Any?): Any?
    open fun findIndex(callback: (element: Any? /* = null */, index: Number, array: Any? /* = null */) -> Boolean, thisArg: Any?): Number
    open fun flat(depth: Number): Any?
    open fun flatMap(callback: (element: Any? /* = null */, index: Number, array: Any? /* = null */) -> Any?, thisArg: Any?): Any?
    open fun forEach(callback: (element: Any? /* = null */, index: Number, array: Any? /* = null */) -> Unit, thisArg: Any?)
    open fun includes(valueToFind: Any?, fromIndex: Number): Boolean
    open fun indexOf(searchElement: Any?, fromIndex: Number): Number
    open fun join(separator: String): String
    open fun keys(): Any?
    open fun lastIndexOf(searchElement: Any?, fromIndex: Number): Number
    open fun map(callback: (element: Any? /* = null */, index: Number, array: Any? /* = null */) -> Any?, thisArg: Any?): Any?
    open fun pop(): Any?
    open fun push(element1: Any?, element2: Any?, element3: Any?, element4: Any?, element5: Any?, element6: Any?, element7: Any?, element8: Any?, element9: Any?, element10: Any?, element11: Any?, element12: Any?, element13: Any?, element14: Any?, element15: Any?, element16: Any?, element17: Any?, element18: Any?, element19: Any?, element20: Any?): Number
    open fun reduce(callback: (accumulator: Any? /* = null */, currentValue: Any? /* = null */, index: Number, array: Any? /* = null */) -> Any?, initialValue: Any?): Any?
    open fun reduceRight(callback: (accumulator: Any? /* = null */, currentValue: Any? /* = null */, index: Number, array: Any? /* = null */) -> Any?, initialValue: Any?): Any?
    open fun reverse(): Any?
    open fun shift(): Any?
    open fun slice(begin: Number, end: Number): Any?
    open fun some(callback: (element: Any? /* = null */, index: Number, array: Any? /* = null */) -> Boolean, thisArg: Any?): Boolean
    open fun sort(compareFunction: (firstEl: Any? /* = null */, secondEl: Any? /* = null */) -> Number): Any?
    open fun splice(start: Number, deleteCount: Number, item1: Any?, item2: Any?, item3: Any?, item4: Any?, item5: Any?, item6: Any?, item7: Any?, item8: Any?, item9: Any?, item10: Any?, item11: Any?, item12: Any?, item13: Any?, item14: Any?, item15: Any?, item16: Any?, item17: Any?, item18: Any?, item19: Any?, item20: Any?): Any?
    open fun toLocaleString(locales: Any?, options: Any?): String
    override fun toString(): String
    open fun unshift(element1: Any?, element2: Any?, element3: Any?, element4: Any?, element5: Any?, element6: Any?, element7: Any?, element8: Any?, element9: Any?, element10: Any?, element11: Any?, element12: Any?, element13: Any?, element14: Any?, element15: Any?, element16: Any?, element17: Any?, element18: Any?, element19: Any?, element20: Any?): Number
    open fun values(): Any?

    companion object {
        fun from(arrayLike: Any?, mapFn: Any?, thisArg: Any?): Any?
        fun isArray(value: Any?): Boolean
        fun of(element1: Any?, element2: Any?, element3: Any?, element4: Any?, element5: Any?, element6: Any?, element7: Any?, element8: Any?, element9: Any?, element10: Any?, element11: Any?, element12: Any?, element13: Any?, element14: Any?, element15: Any?, element16: Any?, element17: Any?, element18: Any?, element19: Any?, element20: Any?): Any?
    }
}