
module.exports.withReturn = (input) => {
    switch(input) {
        case 0: return -1
        case 1: return "a"
        case 2: return -2
        default: return "b"
    }
}

module.exports.withBreak = (input) => {
    let output = null

    switch(input) {
        case 0:
            output = -1
            break
        case 1:
            output = "a"
            break
        case 2:
            output = -2
            break
        default:
            output = "b"
    }

    return output
}

module.exports.withoutBreak = (input) => {
    let output = null

    switch(input) {
        case 0: output = -1
        case 1: output = "a"
        case 2: output = -2
        default: output = "b"
    }

    return output
}

module.exports.withoutDefault = (input) => {
    switch(input) {
        case 0: return "a"
        case 1: return "b"
        case 2: return "c"
    }
}
