
module.exports.withDefault = (a, b) => {
    if (b) {
        if (a) {
            return a
        } else {
            return b
        }
    }

    throw Error()
}
