package com.example.FutureCookies

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.polin.R

public var coin_counter:       Int = 100  // счётчик монет
public var cake_counter:       Int = 5    // счётчик печенек
public var coin_counter_text:       TextView? = null
public var cake_counter_text:       TextView? = null

class MainActivity : AppCompatActivity() {

    private var fortune_label_image:    ImageView? = null
    private var back_btn:               ImageView? = null
    private var break_btn:              ImageView? = null
    private var cake_image:             ImageView? = null
    private var predict_bg_image:       ImageView? = null
    private var predict_text:           TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        cake_image          = findViewById(R.id.cake_image)   //передаём id элементов
        break_btn           = findViewById(R.id.break_btn)
        back_btn            = findViewById(R.id.back_btn)
        predict_bg_image    = findViewById(R.id.predict_bg_image)
        predict_text        = findViewById(R.id.predict_text)
        coin_counter_text   = findViewById(R.id.coin_counter)
        cake_counter_text   = findViewById(R.id.cake_counter)
        fortune_label_image = findViewById(R.id.fortune_label_image)

        coin_counter_text?.text = coin_counter.toString()   // передаём значения счётчиков в текст
        cake_counter_text?.text = cake_counter.toString()

        back_btn?.visibility            = View.INVISIBLE    //скрываем элементы
        predict_bg_image?.visibility    = View.INVISIBLE
        predict_text?.visibility        = View.INVISIBLE

        break_btn?.setOnClickListener{  // обработка нажатия кнопки разламывания

            if (cake_counter <= 0){     // если счётчик печенек на нуле, выпадает уведомление
                Toast.makeText( this, "Недостаточно печенек\nСходите в лотерею", Toast.LENGTH_SHORT ).show()
            }
            else {                      // иначе:
                cake_counter -= 1
                cake_counter_text?.text = cake_counter.toString()
                cake_image?.setImageResource(R.drawable.broken_cake_image)
                break_btn?.visibility = View.INVISIBLE
                back_btn?.visibility = View.VISIBLE
                predict_bg_image?.visibility = View.VISIBLE
                predict_text?.text = resources.getStringArray(R.array.predicts)[(0..14).random()]
                predict_text?.visibility = View.VISIBLE

            }
        }

        back_btn?.setOnClickListener{  // обработка нажатия кнопки возврата
            cake_image?.setImageResource(R.drawable.norm_cake_image)
            break_btn?.visibility           = View.VISIBLE
            back_btn?.visibility            = View.INVISIBLE
            predict_bg_image?.visibility    = View.INVISIBLE
            predict_text?.visibility        = View.INVISIBLE
        }

        fortune_label_image?.setOnClickListener{   // обработка нажатия колеса фортуны
            var fortuneIntent: Intent = Intent(this, FortuneActivity::class.java)
            startActivity(fortuneIntent)
        }
    }
}