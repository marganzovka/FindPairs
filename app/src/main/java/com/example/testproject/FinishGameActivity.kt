package com.example.testproject

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class FinishGameActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finish_game)

        // Получаем время и баланс
        var time = intent.getLongExtra("Time", 0)
        var money = intent.getIntExtra("money", 0)

        // Баланс выигрыша
        var wonMoney = 0
        // Флаг - Применялось ли удваивание?
        var isReward = false

        val buttonBack = findViewById<ImageView>(R.id.buttonBack)

        // Возврат на главное активити с передачей ему баланса
        buttonBack.setOnClickListener {
            val intentToMenu = Intent(this, MainActivity::class.java)
            intentToMenu.putExtra("money", money)
            startActivity(intentToMenu)
            finish()
        }

        // Подсчет выигрыша и общего баланса
        if (time <= 20){
            wonMoney = 100
            money += wonMoney
        }
        else if (time >= 39){
            wonMoney = 10
            money += wonMoney
        }
        else {
            var difference = time - 20
            wonMoney = (100 - (difference * 5)).toInt()
            money += wonMoney


        }

        // Вывод выигрыша
        findViewById<TextView>(R.id.moneyCountView).text = wonMoney.toString()

        // Удваивание выигрыша
        findViewById<Button>(R.id.buttonReward).setOnClickListener {
            if (!isReward){
            money += wonMoney
                wonMoney *= 2
            findViewById<TextView>(R.id.moneyCountView).text = (wonMoney).toString()
            isReward = true
            }

        }

    }

}