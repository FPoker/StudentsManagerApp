package projects.kotlin.cesaestudent

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import projects.kotlin.cesaestudent.adapter.StudentAdapter
import projects.kotlin.cesaestudent.databinding.ActivityStudentDetailsBinding
import projects.kotlin.cesaestudent.dao.viewmodel.CourseViewModel
import projects.kotlin.cesaestudent.dao.viewmodel.MainViewModel


class StudentDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStudentDetailsBinding
    private lateinit var viewModel: MainViewModel
    private val adapter = StudentAdapter()
    private lateinit var courseViewModel: CourseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStudentDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        courseViewModel = ViewModelProvider(this)[CourseViewModel::class.java]

        binding.recyclerStudents.layoutManager = LinearLayoutManager(this)
        binding.recyclerStudents.adapter = adapter

        val id = intent.getIntExtra("STUDENT_ID", 0)

        val tvId: TextView = binding.tvIdStudent
        val studentName: TextView = binding.etStudentName
        val studentAddress: TextView = binding.etAddressStudent
        val studentEmail: TextView = binding.etEmailStudent
        val studentAge: TextView = binding.etAgeStudent
        val studentPhone: TextView = binding.etPhoneStudent
        val tvCourseDesignation: TextView = binding.etCourseDesignation

        viewModel.getStudent(id).observe(this) { student ->
            student?.let {
                tvId.text = "Student with ID: ${student?.id}"
                studentName.text = student.nameStudent
                studentAddress.text = student.addressStudent
                studentEmail.text = student.emailStudent
                studentAge.text = student.ageStudent.toString()
                studentPhone.text = student.phoneStudent.toString()

                val studentCourseId = student.courseId
                courseViewModel.courses.observe(this) { courses ->
                    courses?.let {
                        val selectedCourse = courses.find { it.courseId == studentCourseId }
                        tvCourseDesignation.text =
                            "${selectedCourse?.courseYear} - ${selectedCourse?.courseDesignation}"
                    }
                }
            }
        }

        courseViewModel.courses.observe(this) { courses ->
            courses?.let {
                adapter.updateCourses(it)
            }
        }

        viewModel.getAllStudents()
        courseViewModel.getAllCourses()

        binding.btnGoBack.setOnClickListener {
            finish()
        }

        val student = viewModel.getStudent(id).value

        studentPhone.apply {
            text = student?.phoneStudent.toString()
            setOnClickListener {
                val phoneIntent = Intent(Intent.ACTION_DIAL)
                phoneIntent.data = Uri.parse("tel:${student?.phoneStudent}")
                startActivity(phoneIntent)
            }
        }

        studentEmail.apply {
            text = student?.emailStudent
            setOnClickListener {
                val emailIntent = Intent(Intent.ACTION_SEND)
                emailIntent.type = "text/plain"
                emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(student?.emailStudent))
                emailIntent.putExtra(
                    Intent.EXTRA_SUBJECT,
                    "This Email is sent by Cesae Student App"
                )
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Just testing!")
                startActivity(emailIntent)
            }
        }
    }
}
