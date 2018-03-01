package io

import submission.Submission
import java.io.File

fun Submission.write(file: File) {
    val writer = file.writer()
    assignments.forEach { assignment ->
        writer.write(assignment.rides.size.toString() +
                " " + assignment.rides.joinToString(separator = " ") { it.id.toString() })
    }
    writer.close()
}