package projects.kotlin.cesaestudent

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.gms.common.internal.service.Common.API
import projects.kotlin.cesaestudent.databinding.ActivityWeatherBinding
import projects.kotlin.cesaestudent.model.Weather
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*


class WeatherActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWeatherBinding
    val city: String = "Oporto, pt"
    val api: String = "YourWeatherAPIHere"

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityWeatherBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnGoBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        getWeather()
    }

    private fun getWeather() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(WeatherService::class.java)

        val call = service.getWeather(city, api)

        call.enqueue(object : Callback<Weather> {
            override fun onResponse(call: Call<Weather>, response: Response<Weather>) {
                if (response.isSuccessful) {
                    val weather: Weather? = response.body()

                    binding.address.text = "${weather?.name}, ${weather?.sys?.country}"
                    binding.updatedAt.text =
                        "Updated at: ${
                            SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(
                                Date(weather?.dt?.times(1000) ?: 0)
                            )}"
                    binding.status.text = weather?.weather?.get(0)?.description?.capitalize()
                    binding.temp.text = "${weather?.main?.temp?.toInt()}°C"
                    binding.tempMin.text = "Min Temp: ${weather?.main?.temp_min?.toInt()}°C"
                    binding.tempMax.text = "Max Temp: ${weather?.main?.temp_max?.toInt()}°C"
                    binding.sunrise.text = SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(weather?.sys?.sunrise?.times(1000) ?: 0))
                    binding.sunset.text = SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(weather?.sys?.sunset?.times(1000) ?: 0))
                    binding.wind.text = "${weather?.wind?.speed} m/s"
                    binding.pressure.text = "${weather?.main?.pressure} hPa"
                    binding.humidity.text = "${weather?.main?.humidity} %"

                    binding.loader.visibility = View.GONE
                    binding.mainContainer.visibility = View.VISIBLE
                } else {
                    Toast.makeText(this@WeatherActivity, "Error getting weather data", Toast.LENGTH_SHORT).show()
                    binding.loader.visibility = View.GONE
                    binding.errortext.visibility = View.VISIBLE
                }
            }

            override fun onFailure(call: Call<Weather>, t: Throwable) {
                Toast.makeText(this@WeatherActivity, "Error getting weather data", Toast.LENGTH_SHORT).show()
                binding.loader.visibility = View.GONE
                binding.errortext.visibility = View.VISIBLE
            }
        })
    }

}


