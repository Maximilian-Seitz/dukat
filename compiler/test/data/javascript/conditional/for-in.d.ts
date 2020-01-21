
module.exports.getFirstNumberProperty = (obj) => {
    for(var property in obj) {
        if (typeof obj[property] == "number") {
            return property
        }
    }

    return null
}
