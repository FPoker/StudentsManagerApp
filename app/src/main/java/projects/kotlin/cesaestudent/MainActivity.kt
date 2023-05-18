package projects.kotlin.cesaestudent

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import projects.kotlin.cesaestudent.dao.AppDao
import projects.kotlin.cesaestudent.database.DatabaseHelper
import projects.kotlin.cesaestudent.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var appDao: AppDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("authentication", Context.MODE_PRIVATE)

        val db = DatabaseHelper.getDatabase(this)
        appDao = db.appDao()

        val username = sharedPreferences.getString("username", "")
        if (!username.isNullOrEmpty()) {
            val user = appDao.getUserByUsername(username)
            binding.tvWelcome.text = "Welcome, ${user?.username}"
        }

        binding.llCourse.setOnClickListener {
            startActivity(Intent(this@MainActivity, CourseMainActivity::class.java))
            finish()
        }

        binding.llStudent.setOnClickListener {
            startActivity(Intent(this@MainActivity, StudentMainActivity::class.java))
            finish()
        }

        binding.llMap.setOnClickListener {
            startActivity(Intent(this@MainActivity, MapsActivity::class.java))
            finish()
        }

        binding.llAbout.setOnClickListener {
            startActivity(Intent(this@MainActivity, AboutUsActivity::class.java))
            finish()
        }

        binding.btnLogout.setOnClickListener {
            val editor = sharedPreferences.edit()
            editor.remove("username")
            editor.remove("token")
            editor.apply()

            val intent = Intent(this@MainActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()

        }

        binding.ivWeather.setOnClickListener {
            startActivity(Intent(this@MainActivity, WeatherActivity::class.java))
            finish()
        }
    }
}
