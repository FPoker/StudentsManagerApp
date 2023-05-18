package projects.kotlin.cesaestudent.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import projects.kotlin.cesaestudent.OnStudentListener
import projects.kotlin.cesaestudent.viewholder.StudentViewHolder
import projects.kotlin.cesaestudent.databinding.RowStudentBinding
import projects.kotlin.cesaestudent.model.CourseModel
import projects.kotlin.cesaestudent.model.StudentModel

class StudentAdapter : RecyclerView.Adapter<StudentViewHolder>() {
    private var studentList: List<StudentModel> = listOf()
    private var courseList: List<CourseModel> = listOf()
    private var listener: OnStudentListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val binding = RowStudentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StudentViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val student = studentList[position]
        val course = courseList.filter { it.courseId == student.courseId }
        holder.bind(student, course)
    }

    override fun getItemCount(): Int {
        return studentList.size
    }

    fun updateStudents(list: List<StudentModel>) {
        studentList = list
        notifyDataSetChanged()
    }

    fun updateCourses(list: List<CourseModel>) {
        courseList = list
        notifyDataSetChanged()
    }

    fun attachListener(studentListener: OnStudentListener) {
        listener = studentListener
    }

}