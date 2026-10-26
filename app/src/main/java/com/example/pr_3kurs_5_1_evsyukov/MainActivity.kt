package com.example.pr_3kurs_5_1_evsyukov

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import android.util.Log

class MainActivity : AppCompatActivity() {

    private lateinit var cityEditText: EditText
    private lateinit var resultTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        cityEditText = findViewById(R.id.city)
        resultTextView = findViewById(R.id.tvWeatherResult)
        val btnGetWeather = findViewById<Button>(R.id.btnGetWeather)

        btnGetWeather.setOnClickListener {
            val cityName = cityEditText.text.toString().trim()
            if (cityName.isNotEmpty()) {
                getResult(cityName)
            } else {
                Toast.makeText(this, "Пожалуйста, введите название города", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getResult(city: String) {
        // Ключ API из вашей методички
        val key = "64ca3f64633a57ce365873e3637e0362"

        // Формируем URL. Параметры units=metric и lang=ru сделают температуру в Цельсиях, а описание на русском
        var url="https://api.openweathermap.org/data/2.5/weather?q="+city+"&appid="+key+"&units=metric&lang=ru"

        // Создаем очередь запросов Volley
        val queue = Volley.newRequestQueue(this)

        // Формируем строковый запрос к API
        val stringRequest = StringRequest(
            Request.Method.GET,
            url,
            { response ->
                try {
                    // Парсим полученный текстовый ответ в JSON-объект
                    val mainObject = JSONObject(response)

                    // Получаем данные о городе и стране
                    val cityName = mainObject.getString("name")

                    // ВАЖНО: Температура и давление лежат в объекте "main" (в методичке ошибка!)
                    val mainData = mainObject.getJSONObject("main")
                    val temp = mainData.getDouble("temp")
                    val pressure = mainData.getInt("pressure")

                    // Скорость ветра лежит в объекте "wind"
                    val windData = mainObject.getJSONObject("wind")
                    val windSpeed = windData.getDouble("speed")

                    // Форматируем текст для вывода на экран (Выполнение пункта №7)
                    val outputText = """
                        Город: $cityName
                        Температура: $temp °C
                        Давление: $pressure гПа
                        Скорость ветра: $windSpeed м/с
                    """.trimIndent()

                    // Отображаем на экране телефона
                    resultTextView.text = outputText

                    // Выводим лог для проверки (Выполнение пункта №6)
                    Log.d("MyLog", "Response: $outputText")

                } catch (e: Exception) {
                    Log.d("MyLog", "Ошибка парсинга JSON: ${e.message}")
                    resultTextView.text = "Ошибка обработки данных"
                }
            },
            { error ->
                // Логируем и выводим сетевую ошибку (например, если нет интернета)
                Log.d("MyLog", "Volley error: $error")
                resultTextView.text = "Ошибка сети. Проверьте правильность названия города."
            }
        )

        // Добавляем созданный запрос в очередь для выполнения
        queue.add(stringRequest)
    }
}