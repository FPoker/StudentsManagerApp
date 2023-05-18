package projects.kotlin.cesaestudent

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import projects.kotlin.cesaestudent.adapter.StudentAdapter
import projects.kotlin.cesaestudent.databinding.ActivityCourseEditBinding
import projects.kotlin.cesaestudent.model.CourseModel
import projects.kotlin.cesaestudent.dao.viewmodel.MainViewModel

class CourseEditActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCourseEditBinding
    private lateinit var viewModel: MainViewModel
    private val adapter = StudentAdapter()
    private var course: CourseModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCourseEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        binding.recyclerCourses.layoutManager = LinearLayoutManager(this)
        binding.recyclerCourses.adapter = adapter

        viewModel.courses.observe(this) { courses ->
            courses?.let {
                adapter.updateCourses(it)
            }
        }

        viewModel.getAllCourses()

        val courseId = intent.getIntExtra("COURSE_ID", 0)

        val tvId: TextView = binding.tvId
        val editCourseYear: EditText = binding.editCourseYear
        val editCourseDesignation: EditText = binding.editCourseDesignation

        viewModel.getCourse(courseId).observe(this, Observer<CourseModel?> { course ->
            this.course = course
            tvId.text = "Course ${course?.courseDesignation}"
            editCourseYear.setText(course?.courseYear.toString())
            editCourseDesignation.setText(course?.courseDesignation)
        })

        binding.btnSave.setOnClickListener {
            val newCourse = CourseModel(
                courseId,
                editCourseYear.text.toString().toInt(),
                editCourseDesignation.text.toString()
            )

            viewModel.updateCourse(newCourse)

            Toast.makeText(this, "Course updated", Toast.LENGTH_SHORT).show()

            startActivity(Intent(this, CourseMainActivity::class.java))
            finish()
        }

        binding.btnGoBack.setOnClickListener {
            val intent = Intent(this, CourseMainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}