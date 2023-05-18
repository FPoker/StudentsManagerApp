package projects.kotlin.cesaestudent.model

data class StudentWithCourse(
    var id: Int,
    var nameStudent: String,
    var emailStudent: String,
    var addressStudent: String,
    var ageStudent: Int,
    var phoneStudent: Int,
    var courseId: Int
)