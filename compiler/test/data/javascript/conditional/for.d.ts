
module.exports.countIterations = (nextIteration) => {
    for(var count = 0; nextIteration(); count++) {  }

    return count
}
