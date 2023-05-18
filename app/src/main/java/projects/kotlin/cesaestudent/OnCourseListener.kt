package projects.kotlin.cesaestudent

import projects.kotlin.cesaestudent.model.CourseModel

interface OnCourseListener {
    fun onClick(course: CourseModel)
}