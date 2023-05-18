package projects.kotlin.cesaestudent

import projects.kotlin.cesaestudent.model.StudentModel

interface OnStudentListener {
    fun onClick(student: StudentModel)
}