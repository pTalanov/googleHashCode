package submission

import model.Ride

data class Submission(
    val assignments: List<Assignment>
)

data class Assignment(
        val rides: List<Ride>
)