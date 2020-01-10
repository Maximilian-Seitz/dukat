
function is3D(vector) {
    return vector.z !== undefined && vector.z !== null
}

module.exports.lengthOf = function(vector) {
    return is3D(vector) ? Math.hypot(vector.x, vector.y, vector.z) : Math.hypot(vector.x, vector.y)
}
