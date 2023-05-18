package projects.kotlin.cesaestudent

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import projects.kotlin.cesaestudent.database.DatabaseHelper
import projects.kotlin.cesaestudent.databinding.ActivityLoginBinding
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var databaseHelper: DatabaseHelper

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        databaseHelper = DatabaseHelper.getDatabase(applicationContext)

        val sharedPreferences = this.getSharedPreferences("authentication", Context.MODE_PRIVATE)
        val username = sharedPreferences.getString("username", "")
        val token = sharedPreferences.getString("token", "")

        if (username != null && token != null) {

            if (username != "" && token != "") {

                val user = databaseHelper.appDao().getUserByUsername(username)
                if (user != null) {

                    val currentDate: LocalDate = LocalDate.now()
                    val tokenDate: LocalDate = LocalDate.parse(user.token_date)

                    if (token == user.token_auth && currentDate.isBefore(tokenDate)) {
                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                        finish()
                    }
                } else {
                    Toast.makeText(applicationContext, getString(R.string.toast_auth_again), Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }

        binding.btnSignIn.setOnClickListener {
            val password = binding.etPassword.text.toString()
            val username = binding.etName.text.toString()

            val user = databaseHelper.appDao()?.login(username, password)

            if (user != null) {
                val generatedToken = generateToken()
                keepLoggedIn(sharedPreferences, username, generatedToken)

                user.token_auth = generatedToken
                user.token_date = LocalDate.now().plusDays(1).toString()
                val res = databaseHelper.appDao().refreshToken(user)

                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                finish()
            } else {
                Toast.makeText(
                    this@LoginActivity,
                    "Wrong Name or Password!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        binding.tvRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        binding.tvForgotPassword.setOnClickListener {
            startActivity(Intent(this, RecoverPasswordActivity::class.java))
            finish()
        }
    }

    private fun keepLoggedIn(
        sharedPreferences: SharedPreferences,
        username: String,
        token: String
    ) {
        val editor = sharedPreferences.edit()
        editor.putString("username", username)
        editor.putString("token", token)
        editor.apply()
    }

    private fun generateToken(): String {
        return UUID.randomUUID().toString()
    }
}