package com.example.testproject

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.view.View
import android.widget.Chronometer
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat


class GameScene : AppCompatActivity() {
    lateinit var imageMap : MutableMap<Int,Int>
    lateinit var imageViewArr : Array<Int>
    lateinit var meter : Chronometer
    var deletedCount = 0
    var money = 0


    @SuppressLint("MissingInflatedId", "WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_scene)

        // Получение баланса с Main активити
        money = intent.getIntExtra("money", 0)
        findViewById<TextView>(R.id.moneyCountView).text = money.toString()

        CreateMap()
        // Массив с ImageView
        imageViewArr = arrayOf(
            R.id.cell11,
            R.id.cell12,
            R.id.cell13,
            R.id.cell14,
            R.id.cell21,
            R.id.cell22,
            R.id.cell23,
            R.id.cell24,
            R.id.cell31,
            R.id.cell32,
            R.id.cell33,
            R.id.cell34,
            R.id.cell41,
            R.id.cell42,
            R.id.cell43,
            R.id.cell44,
            R.id.cell51,
            R.id.cell52,
            R.id.cell53,
            R.id.cell54,
        )
        RandomImage(0)
        // Пересоздаем Map для обнуления значений
        CreateMap()
        RandomImage(10)

        // Создание и запуск таймера
        meter = findViewById<Chronometer>(R.id.c_meter)
        meter.start()

        // Флаг - первый ли элемент нажат?
        var isFirstClick = true
        // Переменные для будущего tag изображения, для сравнения
        var firstClick = 0
        var secondClick = 0
        // Сыллки на первые изображения
        lateinit var firstClickImageView : ImageView
        lateinit var secondClickImageView : ImageView

        // Обработчик нажатий
        val clickListener = View.OnClickListener {
            val tag = (it as ImageView).tag as Int
            // Если был нажат первый элемент, то помещаем его в соответствующую переменную и помечаем, меняя цвет
            if (isFirstClick) {
                firstClick = tag
                firstClickImageView = it

                firstClickImageView.background = ContextCompat.getDrawable(
                    this, R.drawable.shape_raunded_container
                )

            } else {
                // Также помещаем второй элемент и меняем цвет. Вызываем метод который будет сравнивать два элемента
                secondClick = tag
                secondClickImageView = it

                secondClickImageView.background = ContextCompat.getDrawable(
                    this, R.drawable.shape_raunded_container
                )
                // Метод отвечающий за сравнение двух элементов
                Comparison(firstClick, secondClick, firstClickImageView, secondClickImageView)
            }

            // Меняем флаг
            isFirstClick = !isFirstClick
        }

        // К каждому View применяем обработчик
        for (i in imageViewArr){
            findViewById<ImageView>(i).setOnClickListener (clickListener)
        }



    }
    // Метод получает два tag и две ссылки на View
    fun Comparison(firstClick: Int, secondClick: Int, firstClickImageView: ImageView, SecondClickImageView: ImageView ){
        if (firstClick == secondClick && firstClickImageView != SecondClickImageView && firstClickImageView.tag != 0){
            // Если tag двух изображений равен, не равен нулю, но при этом это не один и тот же View то обнуляем их tag и удаляем изображение
            firstClickImageView.setImageDrawable(null)
            SecondClickImageView.setImageDrawable(null)
            firstClickImageView.tag = 0
            SecondClickImageView.tag = 0
            deletedCount += 1

        }
        // После нахождения всех 10 пар
        if (deletedCount == 10){
            // Останавливаем таймер
            meter.stop()
            // Считаем в секундах прошедшее время
            val elapsedMillis = (SystemClock.elapsedRealtime() - meter.base) / 1000
            // Передаем время, баланс в Finish Активити и запускаем его
            val intentToFinish = Intent(this, FinishGameActivity::class.java)
            intentToFinish.putExtra("Time", elapsedMillis)
            intentToFinish.putExtra("money", money)
            startActivity(intentToFinish)
            finish()
        }

        // Меняем цвет фона на основной в View
        firstClickImageView.background = ContextCompat.getDrawable(
            this, R.drawable.rounded_container
        )

        SecondClickImageView.background = ContextCompat.getDrawable(
            this, R.drawable.rounded_container
        )
    }


    // Метод который проходит 10 позиций (= количество изображений) по массиву с ImageView, начиная с полученного индекса => нужно использовать его дважды,
    // чтобы покрыть все элементы
    fun RandomImage(index: Int){
        var count = index
        var max = index + 9
        while (count <= max){
            var rands : Int
            // Рандомно получаем число, если оно четное (все ключи картинок четные и после использования меняются на нечетные), то вставляем картинку
            do {
                rands = (0..18).random()
            }while (rands % 2 != 0 || !imageMap.containsKey(rands))
            findViewById<ImageView>(imageViewArr[count]).setImageResource(imageMap[rands]!!)
            // Так же вставляем tag для будущего сравнения в паре
            findViewById<ImageView>(imageViewArr[count]).tag = imageMap[rands]!!
            var newKey = rands + 1
            imageMap[newKey] = imageMap.remove(rands)!!
            // Счетчик для прохода по циклу 10 раз
            count++

        }
    }

    // Создание MAPа с изображениями
    fun CreateMap(){
        imageMap = mutableMapOf(
            0 to R.drawable.icon0,
            2 to R.drawable.icon1,
            4 to R.drawable.icon2,
            6 to R.drawable.icon3,
            8 to R.drawable.icon4,
            10 to R.drawable.icon5,
            12 to R.drawable.icon6,
            14 to R.drawable.icon7,
            16 to R.drawable.icon8,
            18 to R.drawable.icon9)

    }


}