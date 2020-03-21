package com.mehul.quizapp

import AppDB.Model.Player
import CommonUtils.Utils
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_result.*
import java.util.*

class ResultActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        var bundle = intent.extras
        tvGreen.text = bundle?.getInt("score",0).toString()
        var totalQ = Utils.getJsonFromAssets(this,
            bundle?.getString("sub")?.toLowerCase(Locale.ROOT) + ".json")
            .size
        var incorrectAns = totalQ - bundle?.getInt("score",0)!!
        tvRed.text = incorrectAns.toString()

        var percentage = (100 * bundle.getInt("score",0))/totalQ

        when {
            percentage >= 90 -> {
                ivResult.setImageResource(R.drawable.genius)
                tvQuote.text = "You are a genius person"
            }
            percentage in 75..89 -> {
                ivResult.setImageResource(R.drawable.excellent)
                tvQuote.text = "You are an excellent person."
            }
            percentage in 50..74 -> {
                ivResult.setImageResource(R.drawable.average)
                tvQuote.text = "You are an average person."
            }
            else -> {
                ivResult.setImageResource(R.drawable.poor)
                tvQuote.text = "Very poor score.\nTry again!"
            }
        }

        btnDone.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this,HomeActivity::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                .putExtra("player",intent.getParcelableExtra<Player>("player")))
        })

    }
}
