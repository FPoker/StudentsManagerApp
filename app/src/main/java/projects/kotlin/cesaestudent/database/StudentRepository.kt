package projects.kotlin.cesaestudent.database

import android.content.Context
import projects.kotlin.cesaestudent.model.CourseModel
import projects.kotlin.cesaestudent.model.StudentModel

class StudentRepository(context: Context) {

    private val databaseHelper = DatabaseHelper.getDatabase(context).appDao()

    // Students

    fun insertStudent(student: StudentModel): Long {
        return databaseHelper.insertStudent(student)
    }

    fun updateStudent(student: StudentModel): Int {
        return databaseHelper.updateStudent(student)
    }

    fun deleteStudent(student: StudentModel): Int {
        return databaseHelper.deleteStudent(student)
    }

    fun getStudent(id: Int): StudentModel {
        return databaseHelper.getStudent(id)
    }

    fun getAllStudent(): List<StudentModel> {
        return databaseHelper.getAllStudent()
    }

    fun getAllStudentByNameAsc(): List<StudentModel> {
        return databaseHelper.getAllStudentByNameAsc()
    }

    fun getAllStudentByNameDesc(): List<StudentModel> {
        return databaseHelper.getAllStudentByNameDesc()
    }

    // Courses

    fun getAllCourseByNameAsc(): List<CourseModel> {
        return databaseHelper.getAllCourseByNameAsc()
    }

    fun getAllCourseByNameDesc(): List<CourseModel> {
        return databaseHelper.getAllCourseByNameDesc()
    }

    fun getStudentsByCourseId(courseId: Int): List<StudentModel> {
        return databaseHelper.getStudentsByCourseId(courseId)
    }

    fun getStudentsByCourseIdAsc(courseId: Int): List<StudentModel> {
        return databaseHelper.getStudentsByCourseIdAsc(courseId)
    }

    fun getStudentsByCourseIdDesc(courseId: Int): List<StudentModel> {
        return databaseHelper.getStudentsByCourseIdDesc(courseId)
    }

    fun getAllCourses(): List<CourseModel> {
        return databaseHelper.getAllCourses()
    }

    fun getCourse(courseId: Int): CourseModel {
        return databaseHelper.getCourse(courseId)
    }

    fun insertCourse(course: CourseModel): Long {
        return databaseHelper.insertCourse(course)
    }

    fun deleteCourse(course: CourseModel): Int {
        return databaseHelper.deleteCourse(course)
    }

    fun updateCourse(course: CourseModel): Int {
        return databaseHelper.updateCourse(course)
    }
}
