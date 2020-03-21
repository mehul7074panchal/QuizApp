package com.mehul.quizapp

import AppDB.Model.MultipleChoiceQuestion
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import java.util.*
import Adapters.SummaryAdapter
import android.content.Intent
import kotlinx.android.synthetic.main.activity_summary.*


class SummaryActivity : AppCompatActivity() {

    lateinit var trakQuestion: HashMap<MultipleChoiceQuestion,String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_summary)
        val toolbar: Toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        trakQuestion = hashMapOf()
        val b = intent.extras
        trakQuestion = b?.getSerializable("result") as HashMap<MultipleChoiceQuestion, String>
        val map =
            HashMap<String, String?>()
        val lstQuestion:MutableList<MultipleChoiceQuestion> = arrayListOf()
        for ((key, value) in trakQuestion)
        {
            map[key.question] = value
            lstQuestion.add(key)
        }
        val adapter = SummaryAdapter(this,map,lstQuestion)
        lvSummary.adapter =  adapter

        btnContinue.setOnClickListener {
            startActivity(Intent(this,ResultActivity::class.java).putExtras(b))

        }

    }


}
