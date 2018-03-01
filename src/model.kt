package model

data class Model(
        val map: Map,
        val fleetSize: Int,
        val rides: List<Ride>,
        val perRideBonus: Int,
        val numberOfSteps: Long
)

data class Map(val verticalSize: Int, val horizontalSize: Int)
data class Point(val x: Int, val y: Int)
data class Ride(
    val startPoint: Point,
    val endPoint: Point,
    val earliestStart: Long,
    val latestFinish: Long
)