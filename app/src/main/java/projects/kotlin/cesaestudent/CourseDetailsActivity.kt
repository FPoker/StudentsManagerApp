package projects.kotlin.cesaestudent

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import projects.kotlin.cesaestudent.adapter.StudentAdapter
import projects.kotlin.cesaestudent.databinding.ActivityCourseDetailsBinding
import projects.kotlin.cesaestudent.model.CourseModel
import projects.kotlin.cesaestudent.model.StudentModel
import projects.kotlin.cesaestudent.dao.viewmodel.MainViewModel

class CourseDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCourseDetailsBinding
    private lateinit var viewModel: MainViewModel
    private val adapter = StudentAdapter()
    private var course: CourseModel? = null
    private var selectedStudent: StudentModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityCourseDetailsBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        binding.recyclerStudents.layoutManager = LinearLayoutManager(this)
        binding.recyclerStudents.adapter = adapter

        adapter.attachListener(object : OnStudentListener {
            override fun onClick(student: StudentModel) {
                selectedStudent = student
                val message = getString(R.string.you_select) + " " + "${student.nameStudent}"
                Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.courses.observe(this) { courses ->
            courses?.let {
                adapter.updateCourses(it)
            }
        }

        viewModel.getAllCourses()

        val courseId = intent.getIntExtra("COURSE_ID", 0)

        val tvId: TextView = binding.tvId
        val editCourseYear: TextView = binding.editCourseYear
        val editCourseDesignation: TextView = binding.editCourseDesignation

        viewModel.getCourse(courseId).observe(this) { course ->
            this.course = course
            tvId.text = "Information about: ${course?.courseDesignation}"
            editCourseYear.text = course?.courseYear.toString()
            editCourseDesignation.text = course?.courseDesignation
        }

        binding.btnGoBack.setOnClickListener {
            val intent = Intent(this, CourseMainActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnOrderAsc.setOnClickListener {
            viewModel.getStudentsByCourseIdAsc(courseId)
        }

        binding.btnOrderDesc.setOnClickListener {
            viewModel.getStudentsByCourseIdDesc(courseId)
        }

        binding.btnInfo.setOnClickListener {
            selectedStudent?.let { student ->
                val intent = Intent(this, StudentDetailsActivity::class.java).apply {
                    putExtra("STUDENT_ID", student.id)
                }
                startActivity(intent)
            } ?: run {
                Toast.makeText(applicationContext, "Please select a student", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        observeViewModel()
        viewModel.getStudentsByCourseId(courseId)

    }

    private fun observeViewModel() {
        viewModel.students.observe(this) { students ->
            adapter.updateStudents(students)
        }
    }
}