package projects.kotlin.cesaestudent

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import projects.kotlin.cesaestudent.databinding.ActivitySplashScreenBinding


class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivCesae.animate().apply {
            duration = 2000
            alpha(.5f)
            scaleXBy(.5f)
            scaleY(.5f)
            rotationY(360f)
            translationXBy(200f)
        }.withEndAction {
            binding.ivCesae.animate().apply {
                duration = 1000
                alpha(1f)
                scaleXBy(.5f)
                scaleY(.5f)
                rotationY(360f)
                translationXBy(-100f)
            }.withEndAction {
                binding.ivCesae.animate().apply {
                    duration = 1000
                    alpha(1f)
                    scaleX(1f)
                    scaleY(1f)
                    rotationY(0f)
                    translationX(0f)
                }
            }
        }

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }, 6000)

        val gifSplasAnimation = binding.ivSplashAnimation

        Glide.with(this).asGif().load(R.drawable.splashscreenanimation).into(gifSplasAnimation)

    }
}