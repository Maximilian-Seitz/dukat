
function max(a, b) {
    if (a > b)
        return a
    else
        return b
}

function negate(a) {
    if(isNum(a))
        return -a
    else if(isBool(a))
        return !a
    else
        return null
}