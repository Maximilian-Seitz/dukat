package org.jetbrains.dukat.js.type.inbuilt

import org.jetbrains.dukat.js.type.inbuilt.builder.Class

internal val arrayClass by Class {
    number property "length"

    any staticFunction "isArray"("obj" to number)
    number staticFunction "from"()
    number staticFunction "of"()

    thisType function "concat"()
    number function "copyWithin"("target" to number, "start" to number, "end" to number)
    any function "fill"("value" to any, "start" to number, "end" to number)
    any function "find"()
    number function "findIndex"()
    number function "lastIndexOf"()
    any function "pop"()
    number function "push"()
    any function "reverse"()
    any function "shift"()
    any function "unshift"()
    any function "slice"()
    any function "sort"()
    any function "splice"()
    boolean function "includes"("element" to any, "start" to number)
    any function "indexOf"()
    any function "join"()
    any function "keys"()
    any function "entries"()
    any function "values"()
    any function "forEach"()
    any function "filter"()
    any function "flat"()
    any function "flatMap"()
    any function "map"()
    any function "every"()
    any function "some"()
    any function "reduce"()
    any function "reduceRight"()
    string function "toLocaleString"()
    string function "toString"()
}