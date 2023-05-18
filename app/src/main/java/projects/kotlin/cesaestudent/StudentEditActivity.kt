package projects.kotlin.cesaestudent

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import projects.kotlin.cesaestudent.adapter.StudentAdapter
import projects.kotlin.cesaestudent.databinding.ActivityStudentEditBinding
import projects.kotlin.cesaestudent.model.CourseModel
import projects.kotlin.cesaestudent.model.StudentModel
import projects.kotlin.cesaestudent.dao.viewmodel.CourseViewModel
import projects.kotlin.cesaestudent.dao.viewmodel.MainViewModel

class StudentEditActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStudentEditBinding
    private lateinit var viewModel: MainViewModel
    private val adapter = StudentAdapter()
    private lateinit var courseViewModel: CourseViewModel
    private var selectedCourse: CourseModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStudentEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        courseViewModel = ViewModelProvider(this)[CourseViewModel::class.java]

        binding.recyclerStudents.layoutManager = LinearLayoutManager(this)
        binding.recyclerStudents.adapter = adapter

        viewModel.students.observe(this) { students ->
            students?.let {
                adapter.updateStudents(it)
            }
        }

        val id = intent.getIntExtra("STUDENT_ID", 0)

        val tvId: TextView = binding.tvIdStudent
        val studentName: EditText = binding.etStudentName
        val studentAddress: EditText = binding.etAddressStudent
        val studentEmail: EditText = binding.etEmailStudent
        val studentAge: EditText = binding.etAgeStudent
        val studentPhone: EditText = binding.etPhoneStudent
        val spinnerCourses: Spinner = binding.spChooseCourse

        viewModel.getStudent(id).observe(this) { student ->
            student?.let {
                tvId.setText("Student ID: ${student?.id}")
                studentName.setText(student.nameStudent)
                studentAddress.setText(student.addressStudent)
                studentEmail.setText(student.emailStudent)
                studentAge.setText(student.ageStudent.toString())
                studentPhone.setText(student.phoneStudent.toString())
                courseViewModel.getCourse(student.courseId).observe(this) { course ->
                    course?.let {
                        selectedCourse = course
                        spinnerCourses.setSelection(
                            getSpinnerIndex(
                                courseViewModel.courses.value,
                                course
                            )
                        )
                    }
                }
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

        viewModel.courses.observe(this, Observer<List<CourseModel>> { courses ->
            val courseNames = courses.map { it.courseDesignation }
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, courseNames)
            spinnerCourses.adapter = adapter

            viewModel.getStudent(id).observe(this, Observer<StudentModel?> { student ->
                tvId.setText("Student ID: ${student?.id}")
                studentName.setText(student?.nameStudent)
                studentAddress.setText(student?.addressStudent)
                studentEmail.setText(student?.emailStudent)
                studentAge.setText(student?.ageStudent.toString())
                studentPhone.setText(student?.phoneStudent.toString())

                student?.courseId?.let { courseId ->
                    val index = courses.indexOfLast { it.courseId == courseId }
                    if (index >= 0) {
                        spinnerCourses.setSelection(index)
                        selectedCourse = courses[index]
                    }
                }
            })
        })

        binding.btnSave.setOnClickListener {
            val student = StudentModel(
                id,
                selectedCourse?.courseId ?: 0,
                studentName.text.toString(),
                studentAddress.text.toString(),
                studentEmail.text.toString(),
                studentAge.text.toString().toInt(),
                studentPhone.text.toString().toInt()
            )

            viewModel.updateStudent(student)

            setResult(RESULT_OK, Intent().apply {
                putExtra("STUDENT_ID", id)
            })
            val intent = Intent(this, StudentMainActivity::class.java)

            Toast.makeText(this, "Student updated", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, StudentMainActivity::class.java))
            finish()
        }

        binding.btnGoBack.setOnClickListener {
            startActivity(Intent(this, StudentMainActivity::class.java))
            finish()
        }
    }

    private fun getSpinnerIndex(courses: List<CourseModel>?, course: CourseModel?): Int {
        if (courses == null || course == null) {
            return -1
        }
        for (i in courses.indices) {
            if (courses[i].courseId == course.courseId) {
                return i
            }
        }
        return -1
    }
}


