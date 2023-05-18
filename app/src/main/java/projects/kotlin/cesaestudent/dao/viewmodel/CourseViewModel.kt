package projects.kotlin.cesaestudent.dao.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import projects.kotlin.cesaestudent.database.StudentRepository
import projects.kotlin.cesaestudent.model.CourseModel

class CourseViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = StudentRepository(application.applicationContext)

    private val _courses = MutableLiveData<List<CourseModel>>()
    val courses: LiveData<List<CourseModel>> get() = _courses

    fun getAllCourses() {
        _courses.value = repository.getAllCourses()
    }

    fun getCourse(courseId: Int): LiveData<CourseModel?> {
        val course = repository.getCourse(courseId)
        return MutableLiveData<CourseModel?>(course)
    }
}
