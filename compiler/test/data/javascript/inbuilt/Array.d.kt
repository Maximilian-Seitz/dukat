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

external open class Array {
    open var length: Number
    open fun concat(): Any?
    open fun copyWithin(target: Number, start: Number, end: Number): Any?
    open fun fill(value: Any?, start: Number, end: Number): Any?
    open fun find(): Any?
    open fun findIndex(): Number
    open fun lastIndexOf(): Number
    open fun pop(): Any?
    open fun push(): Number
    open fun reverse(): Any?
    open fun shift(): Any?
    open fun unshift(): Any?
    open fun slice(): Any?
    open fun sort(): Any?
    open fun splice(): Any?
    open fun includes(element: Any?, start: Number): Boolean
    open fun indexOf(): Number
    open fun join(): Any?
    open fun keys(): Any?
    open fun entries(): Any?
    open fun values(): Any?
    open fun forEach(): Any?
    open fun filter(): Any?
    open fun flat(): Any?
    open fun flatMap(): Any?
    open fun map(): Any?
    open fun every(): Any?
    open fun some(): Any?
    open fun reduce(): Any?
    open fun reduceRight(): Any?
    open fun toLocaleString(): String
    override fun toString(): String

    companion object {
        fun isArray(value: Any?): Boolean
        fun from(arrayLike: Any?, mapFn: Any?, thisArg: Any?): Any?
        fun of(): Any?
    }
}