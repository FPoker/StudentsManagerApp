package projects.kotlin.cesaestudent.viewholder

import androidx.recyclerview.widget.RecyclerView
import projects.kotlin.cesaestudent.OnCourseListener
import projects.kotlin.cesaestudent.databinding.RowCourseBinding
import projects.kotlin.cesaestudent.model.CourseModel

class CourseViewHolder(private val bind: RowCourseBinding, private val listener: OnCourseListener) :
    RecyclerView.ViewHolder(bind.root) {

    fun bind(course: CourseModel) {
        bind.tvCourseYear.text = "${course.courseYear}"
        bind.tvCourseDesignation.text = course.courseDesignation
        bind.tvCourseDesignation.setOnClickListener {
            listener.onClick(course)
        }

        bind.tvCourseYear.setOnClickListener {
            listener.onClick(course)
        }

        bind.clCourseRow.setOnClickListener {
            listener.onClick(course)
        }
    }
}