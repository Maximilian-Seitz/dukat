
module.exports.withDefault = (a, b) => {
    if (!b) {
        throw Error()
    }

    if (a) {
        return a
    } else {
        return b
    }
}
