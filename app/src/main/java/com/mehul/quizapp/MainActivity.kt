package com.mehul.quizapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity() {

    private val DELAY: Long = 3000

    private var scheduled = false
    private var splashTimer: Timer? = null

    override fun onDestroy() {
        super.onDestroy()
        if (scheduled) {
            splashTimer!!.cancel()
            splashTimer!!.purge()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        splashTimer = Timer()


        splashTimer = Timer()
        splashTimer!!.schedule(object : TimerTask() {

            override fun run() {
                this@MainActivity.finish()

                startActivity(Intent(this@MainActivity,AddPlayerActivity::class.java))

            }
        }, DELAY)
        scheduled = true
    }
}


