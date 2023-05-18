package projects.kotlin.cesaestudent

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import projects.kotlin.cesaestudent.databinding.ActivityRecoverPasswordBinding

class RecoverPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecoverPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecoverPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSendEmail.setOnClickListener {
            val emailAddress = binding.etEmail.text.toString()
            if (emailAddress.isNotEmpty()) {
                val emailIntent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_EMAIL, arrayOf(emailAddress))
                    putExtra(Intent.EXTRA_SUBJECT, "Password Recovery")
                    putExtra(Intent.EXTRA_TEXT, "Please reset my password.")
                }
                Toast.makeText(this, "Email sent. Please check your email.", Toast.LENGTH_SHORT)
                    .show()
                startActivity(Intent.createChooser(emailIntent, "Send email"))
                finish()
            } else {
                Toast.makeText(this, "You must provide a valid email", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnGoBack.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}