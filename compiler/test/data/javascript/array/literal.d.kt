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
    fun concat(): Any?
    fun copyWithin(target: Number, start: Number, end: Number): Any?
    fun fill(value: Any?, start: Number, end: Number): Any?
    fun find(): Any?
    fun findIndex(): Number
    fun lastIndexOf(): Number
    fun pop(): Any?
    fun push(): Number
    fun reverse(): Any?
    fun shift(): Any?
    fun unshift(): Any?
    fun slice(): Any?
    fun sort(): Any?
    fun splice(): Any?
    fun includes(element: Any?, start: Number): Boolean
    fun indexOf(): Number
    fun join(): Any?
    fun keys(): Any?
    fun entries(): Any?
    fun values(): Any?
    fun forEach(): Any?
    fun filter(): Any?
    fun flat(): Any?
    fun flatMap(): Any?
    fun map(): Any?
    fun every(): Any?
    fun some(): Any?
    fun reduce(): Any?
    fun reduceRight(): Any?
    fun toLocaleString(): String
    fun toString(): String
}