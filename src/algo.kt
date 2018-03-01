package algo

import model.Model
import model.Point
import model.Ride
import submission.Assignment
import submission.Submission
import kotlin.math.absoluteValue
import kotlin.math.max

const val RIDES_CONSIDERED = 5
const val DEPTH = 5

fun algo(model: Model): Submission {
    var currentModel = model
    val assignments = mutableListOf<Assignment>()
    repeat(model.fleetSize) {
        val (newModel, assignment) = chooseAssignment(currentModel)
        assignments.add(assignment)
        currentModel = newModel
    }
    return Submission(assignments)
}

fun chooseAssignment(model: Model): Pair<Model, Assignment> {
    val startNode = Node(State(Point(0, 0), 0), emptyList())
    var node = startNode
    while (true) {
        node = step(model, node) ?: break
    }
    val newModel = model.copy(rides = model.rides - node.takenRides)
    return Pair(newModel, Assignment(node.takenRides))
}

fun step(model: Model, inputNode: Node): Node? {

    fun Node.f(): List<Node> {
        val rides = model.rides
        val sortedRides = rides.sortedBy { it.earliestStart + distance(it.startPoint, state.location) }

        return sortedRides.take(RIDES_CONSIDERED).map {
            Node(this.state.takeRide(it), this.takenRides + it)
        }
    }

    var nodes = listOf(inputNode)
    repeat(DEPTH) {
        nodes = nodes.flatMap { it.f() }
    }
    val bestNode = nodes.maxBy { it.takenRides.reward() } ?: return null
    val chosenRide = bestNode.takenRides.first()
    return inputNode.takeRide(chosenRide)
}

private fun List<Ride>.reward(): Int {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
}


data class Node(val state: State, val takenRides: List<Ride>)

fun Node.takeRide(ride: Ride): Node {
    return Node(state.takeRide(ride), takenRides + ride)
}

data class State(val location: Point, val time: Long)

fun State.takeRide(ride: Ride): State {
    val timeToTravel = distance(location, ride.startPoint)
    val timeOfArrival = time + timeToTravel
    val startTime = max(ride.earliestStart, timeOfArrival)
    val endTime = startTime + distance(ride.startPoint, ride.endPoint)
    return algo.State(ride.endPoint, endTime)
}

fun distance(p1: Point, p2: Point): Int {
    return (p1.x - p2.x).absoluteValue + (p1.y - p2.y).absoluteValue
}