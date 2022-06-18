package com.example.FutureCookies

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.animation.doOnEnd
import com.example.polin.R

class FortuneActivity : AppCompatActivity() {

    private var randValue: Int = 0      // нач значение для последющей ф-ии рандома
    private var curAngle: Float = 0f    // нач угол колеса

    private var private_coin_counter_text:       TextView? = null   // счётчик монет
    private var private_cake_counter_text:       TextView? = null   // счётчик печенек

    private var fortune_wheel:  ImageView? = null   // колесо
    private var rotate_button:  ImageView? = null   // кнопка вращения
    private var back_button:    ImageView? = null   // кнопка возврата

    override fun onPause() {  // передаём значения счётчиков из глобальных переменных
        super.onPause()
        coin_counter_text?.text = coin_counter.toString()
        cake_counter_text?.text = cake_counter.toString()
    }

    @SuppressLint("ObjectAnimatorBinding")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fortune)

        fortune_wheel   = findViewById(R.id.fortune_wheel)  // передаём id элементов
        rotate_button   = findViewById(R.id.rotate_button)
        back_button     = findViewById(R.id.fortune_label_image2)

        private_coin_counter_text   = findViewById(R.id.coin_counter2)
        private_cake_counter_text   = findViewById(R.id.cake_counter2)

        private_coin_counter_text?.text = coin_counter.toString()
        private_cake_counter_text?.text = cake_counter.toString()

        rotate_button?.setOnClickListener{  // обработка нажания на кнопку вращения
            if (coin_counter < 10){         // если монет не достаточно
                Toast.makeText( this, "Недостаточно денег", Toast.LENGTH_SHORT ).show()
            }
            else {                          // иначе:
                coin_counter -= 10
                private_coin_counter_text?.text = coin_counter.toString()
                randValue                  = (0..7).random() * 45
                var randCountOfRotate: Int = (2..8).random()

                val rotateAnimator = ObjectAnimator.ofFloat(
                    fortune_wheel,
                    "rotation",
                    curAngle,
                    randCountOfRotate.toFloat() * 360 + randValue.toFloat()
                )

                curAngle = randCountOfRotate.toFloat() * 360 + randValue.toFloat() // новое значение для угла повторота


                while (curAngle < 0) // держим в пределе положительного значения
                    curAngle += 360

                curAngle %= 360      // сокращаем

                rotateAnimator.duration = 2000   // продолжительность анимации
                rotateAnimator.start()

                back_button?.setAlpha(0.6f)     // меняем прозрачность кнопок
                rotate_button?.setAlpha(0.6f)
                back_button?.isEnabled = false  // делаем кнопки неактивными на время вращения
                rotate_button?.isEnabled = false

                rotateAnimator.doOnEnd {
                    rotate_button?.setAlpha(1f)   // возврвщаем кнопки назад
                    rotate_button?.isEnabled = true
                    back_button?.setAlpha(1f)
                    back_button?.isEnabled = true

                    when(randValue/45){           // выдаём призы
                        0 -> cake_counter += 2
                        1 -> coin_counter += 10
                        2 -> coin_counter += 20
                        3 -> cake_counter += 1
                        4 -> coin_counter += 20
                        5 -> cake_counter += 1
                        6 -> cake_counter += 2
                        7 -> coin_counter += 10
                    }

                    private_coin_counter_text?.text = coin_counter.toString()  // изменяем значение счётчиков
                    private_cake_counter_text?.text = cake_counter.toString()

                }
            }
        }

        back_button?.setOnClickListener{  // обратотка нажатия кнопки назад
            //var mainIntent: Intent = Intent(this, com.example.polin.MainActivity::class.java)
            //startActivity(mainIntent)
            this.finish()
        }
    }
}