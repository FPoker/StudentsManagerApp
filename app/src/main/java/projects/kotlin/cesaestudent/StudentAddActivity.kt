package projects.kotlin.cesaestudent

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import projects.kotlin.cesaestudent.adapter.StudentAdapter
import projects.kotlin.cesaestudent.databinding.ActivityStudentAddBinding
import projects.kotlin.cesaestudent.model.CourseModel
import projects.kotlin.cesaestudent.model.StudentModel
import projects.kotlin.cesaestudent.dao.viewmodel.CourseViewModel
import projects.kotlin.cesaestudent.dao.viewmodel.MainViewModel

class StudentAddActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStudentAddBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var courseViewModel: CourseViewModel
    private val adapter = StudentAdapter()

    private var selectedCourse: CourseModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStudentAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        courseViewModel = ViewModelProvider(this)[CourseViewModel::class.java]

        binding.recyclerStudents.layoutManager = LinearLayoutManager(this)
        binding.recyclerStudents.adapter = adapter

        val listener = object : OnStudentListener {
            override fun onClick(student: StudentModel) {
                val intent = Intent()
                intent.putExtra(EXTRA_STUDENT_ID, student.id)
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
        }
        adapter.attachListener(listener)

        viewModel.students.observe(this) { students ->
            students?.let {
                adapter.updateStudents(it)
            }
        }

        courseViewModel.courses.observe(this) { courses ->
            courses?.let {
                adapter.updateCourses(it)
                binding.spChooseCourse.adapter = object : ArrayAdapter<CourseModel>(this, 0, it) {
                    override fun getView(
                        position: Int,
                        convertView: View?,
                        parent: ViewGroup
                    ): View {
                        val view = convertView ?: LayoutInflater.from(context)
                            .inflate(R.layout.spinner_item_course, parent, false)
                        val course = getItem(position)
                        val courseYearTextView =
                            view.findViewById<TextView>(R.id.spinner_item_course_year)
                        val courseDesignationTextView =
                            view.findViewById<TextView>(R.id.spinner_item_course_designation)
                        courseYearTextView.text = course?.courseYear.toString()
                        courseDesignationTextView.text = course?.courseDesignation
                        return view
                    }

                    override fun getDropDownView(
                        position: Int,
                        convertView: View?,
                        parent: ViewGroup
                    ): View {
                        return getView(position, convertView, parent)
                    }
                }

                binding.spChooseCourse.onItemSelectedListener =
                    object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(
                            parent: AdapterView<*>?,
                            view: View?,
                            position: Int,
                            id: Long
                        ) {
                            selectedCourse =
                                if (position >= 0 && position < it.size) it[position] else null
                        }

                        override fun onNothingSelected(parent: AdapterView<*>?) {
                            selectedCourse = null
                        }
                    }
            }
        }

        courseViewModel.getAllCourses()

        binding.btnSave.setOnClickListener {
            if (selectedCourse == null) {
                Toast.makeText(this, "Please select a course", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val nameStudent = binding.etStudentName.text.toString()
            val addressStudent = binding.etAddressStudent.text.toString()
            val emailStudent = binding.etEmailStudent.text.toString()
            val ageStudent = binding.etAgeStudent.text.toString().toIntOrNull()
            val phoneStudent = binding.etPhoneStudent.text.toString().toIntOrNull()

            if (nameStudent.isNotEmpty() && addressStudent.isNotEmpty() && emailStudent.isNotEmpty() && ageStudent != null && phoneStudent != null) {
                StudentModel(
                    0,
                    selectedCourse!!.courseId,
                    nameStudent,
                    addressStudent,
                    emailStudent,
                    ageStudent,
                    phoneStudent
                )
                viewModel.insertStudent(
                    nameStudent,
                    addressStudent,
                    emailStudent,
                    ageStudent,
                    phoneStudent,
                    selectedCourse!!.courseId
                )
                binding.etStudentName.text.clear()
                binding.etAddressStudent.text.clear()
                binding.etEmailStudent.text.clear()
                binding.etAgeStudent.text.clear()
                binding.etPhoneStudent.text.clear()
                startActivity(Intent(this, StudentMainActivity::class.java))
                finish()
            } else {
                Log.d(binding.etStudentName.text.toString(), "${binding.etStudentName.text}");
                Log.d(binding.etAddressStudent.text.toString(), "${binding.etAddressStudent.text}");
                Log.d(binding.etEmailStudent.text.toString(), "${binding.etEmailStudent.text}");
                Log.d(binding.etAgeStudent.text.toString(), "${binding.etAgeStudent.text}");
                Log.d(binding.etPhoneStudent.text.toString(), "${binding.etPhoneStudent.text}");
                Toast.makeText(this, "Please enter all the fields", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnGoBack.setOnClickListener {
            val intent = Intent(this, StudentMainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    companion object {
        const val EXTRA_STUDENT_ID = "extra_student_id"
    }
}

