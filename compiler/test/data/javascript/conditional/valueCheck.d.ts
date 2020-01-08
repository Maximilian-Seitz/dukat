
module.exports.assertIsZero = (value) => {
    if (value === 0) {
        return
    }

    throw new Error()
}
