package projects.kotlin.cesaestudent.viewholder

import androidx.recyclerview.widget.RecyclerView
import projects.kotlin.cesaestudent.OnStudentListener
import projects.kotlin.cesaestudent.databinding.RowStudentBinding
import projects.kotlin.cesaestudent.model.CourseModel
import projects.kotlin.cesaestudent.model.StudentModel

class StudentViewHolder(
    private val bind: RowStudentBinding,
    private val listener: OnStudentListener?
) : RecyclerView.ViewHolder(bind.root) {

    fun bind(student: StudentModel, course: List<CourseModel>) {

        bind.tvStudentName.text = student.nameStudent
        bind.tvStudentEmail.text = student.emailStudent

        bind.tvStudentName.setOnClickListener {
            listener?.onClick(student)
        }

        bind.tvStudentEmail.setOnClickListener {
            listener?.onClick(student)
        }

        bind.llRowStudent.setOnClickListener {
            listener?.onClick(student)
        }


    }
}