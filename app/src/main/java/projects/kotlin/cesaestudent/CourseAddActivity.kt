package projects.kotlin.cesaestudent

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import projects.kotlin.cesaestudent.adapter.StudentAdapter
import projects.kotlin.cesaestudent.databinding.ActivityCourseAddBinding
import projects.kotlin.cesaestudent.model.CourseModel
import projects.kotlin.cesaestudent.dao.viewmodel.MainViewModel

class CourseAddActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCourseAddBinding
    private lateinit var viewModel: MainViewModel
    private val adapter = StudentAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCourseAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        binding.recyclerCourses.layoutManager = LinearLayoutManager(this)
        binding.recyclerCourses.adapter = adapter

        object : OnCourseListener {
            override fun onClick(course: CourseModel) {
                val intent = Intent()
                intent.putExtra(EXTRA_COURSE_ID, course.courseId)
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
        }

        viewModel.courses.observe(this) { courses ->
            courses?.let {
                adapter.updateCourses(it)
            }
        }

        viewModel.getAllCourses()

        binding.btnSave.setOnClickListener {
            val year = binding.editCourseYear.text.toString()
            val designation = binding.editCourseDesignation.text.toString()
            if (year.isNotEmpty() && designation.isNotEmpty()) {
                val course = CourseModel(0, year.toInt(), designation)
                viewModel.insertCourse(course)
                binding.editCourseYear.text.clear()
                binding.editCourseDesignation.text.clear()
                startActivity(Intent(this, CourseMainActivity::class.java))
                finish()
                Toast.makeText(this, "Course added successfully", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnGoBack.setOnClickListener {
            val intent = Intent(this, CourseMainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    companion object {
        const val EXTRA_COURSE_ID = "extra_course_id"
    }
}