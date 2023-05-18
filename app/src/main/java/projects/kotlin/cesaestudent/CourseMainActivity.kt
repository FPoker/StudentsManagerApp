package projects.kotlin.cesaestudent

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import projects.kotlin.cesaestudent.adapter.CourseAdapter
import projects.kotlin.cesaestudent.databinding.ActivityCourseMainBinding
import projects.kotlin.cesaestudent.model.CourseModel
import projects.kotlin.cesaestudent.dao.viewmodel.MainViewModel

class CourseMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCourseMainBinding
    private lateinit var viewModel: MainViewModel
    private val adapter = CourseAdapter()
    private var courseId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCourseMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        binding.recyclerCourses.layoutManager = LinearLayoutManager(this)
        binding.recyclerCourses.adapter = adapter

        val listener = object : OnCourseListener {
            override fun onClick(course: CourseModel) {
                val message =
                    "You selected the course: ${course.courseYear} - ${course.courseDesignation}"
                Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
                courseId = course.courseId
            }
        }
        adapter.attachListener(listener)

        binding.btnAdd.setOnClickListener {
            startActivity(Intent(this, CourseAddActivity::class.java))
        }

        binding.btnEdit.setOnClickListener {
            if (courseId != 0) {
                val intent = Intent(this, CourseEditActivity::class.java).apply {
                    putExtra("COURSE_ID", courseId)
                }
                startActivity(intent)
            } else {
                Toast.makeText(applicationContext, "Please select a course", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        binding.btnDelete.setOnClickListener {
            if (courseId != 0) {
                viewModel.deleteCourse(courseId)
                viewModel.getAllCourses()
            } else {
                Toast.makeText(applicationContext, "Please select a course", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        binding.btnGoBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnDetails.setOnClickListener {
            if (courseId != 0) {
                val intent = Intent(this, CourseDetailsActivity::class.java).apply {
                    putExtra("COURSE_ID", courseId)
                }
                startActivity(intent)
            } else {
                Toast.makeText(applicationContext, "Please select a course", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        binding.btnOrderAsc.setOnClickListener {
            viewModel.getAllCourseByNameAsc()
        }

        binding.btnOrderDesc.setOnClickListener {
            viewModel.getAllCourseByNameDesc()
        }

        observeViewModel()
        viewModel.getAllCourses()
    }

    private fun observeViewModel() {
        viewModel.courses.observe(this) { courses ->
            adapter.updateCourses(courses)
        }
    }
}
