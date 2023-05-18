package projects.kotlin.cesaestudent

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import projects.kotlin.cesaestudent.adapter.StudentAdapter
import projects.kotlin.cesaestudent.databinding.ActivityStudentMainBinding
import projects.kotlin.cesaestudent.model.StudentModel
import projects.kotlin.cesaestudent.dao.viewmodel.MainViewModel

class StudentMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStudentMainBinding
    private lateinit var viewModel: MainViewModel
    private val adapter = StudentAdapter()
    private var selectedStudent: StudentModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStudentMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        binding.recyclerStudents.layoutManager = LinearLayoutManager(this)
        binding.recyclerStudents.adapter = adapter

        adapter.attachListener(object : OnStudentListener {
            override fun onClick(student: StudentModel) {
                selectedStudent = student
                val message = "${student.nameStudent}, ${student.id}"
                Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
            }
        })

        binding.btnAdd.setOnClickListener {
            startActivity(Intent(this, StudentAddActivity::class.java))
        }

        binding.btnEdit.setOnClickListener {
            selectedStudent?.let { student ->
                val intent = Intent(this, StudentEditActivity::class.java).apply {
                    putExtra("STUDENT_ID", student.id)
                }
                startActivity(intent)
            } ?: run {
                Toast.makeText(applicationContext, "Please select a student", Toast.LENGTH_SHORT)
                    .show()
            }
            finish()
        }

        binding.btnDelete.setOnClickListener {
            selectedStudent?.let { student ->
                viewModel.deleteStudent(student.id)
                viewModel.getAllStudents()
            } ?: run {
                Toast.makeText(applicationContext, "Please select a student", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        binding.btnGoBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnDetails.setOnClickListener {
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

        binding.btnOrderAsc.setOnClickListener {
            viewModel.getAllStudentByNameAsc()
        }

        binding.btnOrderDesc.setOnClickListener {
            viewModel.getAllStudentByNameDesc()
        }

        observeViewModel()
        viewModel.getAllStudents()
    }

    private fun observeViewModel() {
        viewModel.students.observe(this) { students ->
            adapter.updateStudents(students)
        }
    }
}
