package projects.kotlin.cesaestudent

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import projects.kotlin.cesaestudent.database.DatabaseHelper
import projects.kotlin.cesaestudent.databinding.ActivityRegisterBinding
import projects.kotlin.cesaestudent.model.UserModel

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        databaseHelper = DatabaseHelper.getDatabase(applicationContext)

        binding.btnRegister.setOnClickListener {
            val username = binding.etName.text.toString()
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            val passwordConf = binding.etPasswordConf.text.toString()

            if (username != "" && email != "" && password != "" && passwordConf != "" && password == passwordConf) {
                val userModel =
                    UserModel(username = username, password = password, email = email)

                val res = databaseHelper.appDao()?.register(userModel)

                if (res != null) {
                    if (res > 0) {
                        Toast.makeText(applicationContext, "Registration Done!", Toast.LENGTH_SHORT)
                            .show()
                        finish()
                    } else {
                        Toast.makeText(
                            applicationContext,
                            "Registration Failed!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } else {
                if (password != passwordConf) {
                    Toast.makeText(
                        applicationContext,
                        "The Password and Password Confirmation don't match!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        binding.btnGoBack.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}