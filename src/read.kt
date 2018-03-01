package io

import model.Map
import model.Model
import model.Point
import model.Ride
import java.io.File

fun main(args: Array<String>) {
    val file = File("data/exampleInput.txt")
    val model = readFromFile(file)
    val submission = algo.algo(model)
    println(submission)
    println(Evaluation.Evaluation.getTotalReward(model.perRideBonus, submission))
}

private fun readFromFile(file: File): Model {
    val lines = file.readLines()
    val firstLine = lines.first()
    val (R, C, F, N, B, T) = firstLine.toNumbers()

    val rides = (0 until N).map {
        val (a, b, x, y, s, f) = lines[it.toInt() + 1].toNumbers()
        Ride(
                id = it.toInt(),
                startPoint = Point(a.toInt(), b.toInt()),
                endPoint = Point(x.toInt(), y.toInt()),
                earliestStart = s,
                latestFinish = f
        )
    }
    return Model(
            map = Map(R.toInt(), C.toInt()),
            fleetSize = F.toInt(),
            rides = rides,
            perRideBonus = B.toInt(),
            numberOfSteps = T
    )
}

private fun String.toNumbers() = split(" ").map { it.toLong() }

operator fun <T> List<T>.component6(): T = this[5]