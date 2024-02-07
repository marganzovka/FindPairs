package com.example.testproject

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    var money = 0
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val buttonPolice = findViewById<ImageView>(R.id.buttonPolice)
        val buttonSetting = findViewById<ImageView>(R.id.buttonSetting)
        val buttonStart = findViewById<Button>(R.id.buttonStart)
        var moneyCountView = findViewById<TextView>(R.id.moneyCountView)
        // Получение значения монеток с Finish активити
        money = intent.getIntExtra("money", 0)
        // Если пришел нуль, (что бывает, когда приходить еще нечему при открытии игры), то получаем сохраненное кол-во
        if (money == 0){
            val sharedPref = getPreferences(Context.MODE_PRIVATE)
            money = sharedPref.getInt("money", 0)
        }

        // Обновление баланса
        moneyCountView.text = money.toString()


        // Начало игры
        buttonStart.setOnClickListener {
            val intentToGame = Intent(this, GameScene::class.java)
            intentToGame.putExtra("money", money)
            startActivity(intentToGame)

        }

        buttonPolice.setOnClickListener {
            Toast.makeText(this, "Privacy Policy button", Toast.LENGTH_SHORT).show()
        }

        buttonSetting.setOnClickListener {
            Toast.makeText(this, "Setting button", Toast.LENGTH_SHORT).show()
        }

    }

    // Сохранение значения при закрытии игры
    override fun onStop() {
        super.onStop()
        val sharedPref = getPreferences(Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putInt("money", money)
            apply()
        }
    }
}