package com.mehul.quizapp

import AppDB.Controller.ScoreboardLogic
import AppDB.Model.MultipleChoiceQuestion
import AppDB.Model.Player
import AppDB.Model.Scoreboard
import CommonUtils.Utils
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_quiz.*
import java.lang.Exception
import java.util.*
import kotlin.collections.HashMap


class QuizActivity : AppCompatActivity() {

    lateinit var questionList: MutableList<MultipleChoiceQuestion>
    var score = 0
    var quid = 0
    lateinit var currentQuestion: MultipleChoiceQuestion
    lateinit var trakQuestion: HashMap<MultipleChoiceQuestion, String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)
        val toolbar: Toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        trakQuestion = hashMapOf()

        questionList = Utils.getJsonFromAssets(
            this, intent.getStringExtra("sub").toLowerCase(
                Locale.ROOT
            ) + ".json"
        ).toMutableList()
        questionList.shuffle();
        currentQuestion = questionList[quid];
        setQuestion()

        btnNext.setOnClickListener {
            val answer = findViewById<RadioButton>(radioGroup_MCQ.checkedRadioButtonId)


            if (answer == null) {
                Toast.makeText(this, "Select your answer", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            trakQuestion[currentQuestion] = answer.text.toString()
            if (Utils.stringCon(currentQuestion.correct_answer) ==Utils.stringCon(answer.text.toString())) {

                score++
                Log.d("Score", "Your score: $score")
            }

            radioGroup_MCQ.clearCheck()
            if (quid < questionList.size) {
                currentQuestion = questionList[quid]
                setQuestion()
            } else {
                val player = intent.getParcelableExtra<Player>("player")
                val realm = Realm.getDefaultInstance()
                val scoreboardLogic = ScoreboardLogic(realm);
                val scoreboard = scoreboardLogic.getScoreboardByPlayer(
                    player.PlayerId,
                    true,
                    intent.getStringExtra("sub")
                ).toList()
                if (scoreboard.isNotEmpty()) {
                    try {


                        realm.beginTransaction()
                        val scoreb = scoreboard.first()
                        scoreb.score = score
                        realm.insertOrUpdate(scoreb)
                        realm.commitTransaction()

                      //  scoreboardLogic.addOrUpdateScoreboard(scoreb)
                    } catch (e: Exception) {
                        var exr = e.localizedMessage
                        Toast.makeText(this, exr, Toast.LENGTH_LONG).show()
                    }
                } else {
                    val scoreboard = Scoreboard()
                    scoreboard.PlayerId = player.PlayerId
                    scoreboard.ScoreboardId = scoreboardLogic.getNextKey()
                    scoreboard.score = score
                    scoreboard.Subject = intent.getStringExtra("sub")
                    scoreboard.isAttempt = true
                    scoreboardLogic.addOrUpdateScoreboard(scoreboard)
                }
                val intentSummary = Intent(this, SummaryActivity::class.java)
                val b = Bundle()

                b.putInt("score", score)
                b.putSerializable("result", trakQuestion)
                b.putString("sub", intent.getStringExtra("sub"))
                intentSummary.putExtras(b)
                intentSummary.putExtra("player",intent.getParcelableExtra<Player>("player"))

                startActivity(intentSummary)
                finish()
            }

        }

    }


    @SuppressLint("SetTextI18n")
    private fun setQuestion() {

        tv_q_counter.text = (quid + 1).toString() + "/" + questionList.size
        tv_question.text = (quid + 1).toString() + ". " + Utils.stringCon(currentQuestion.question)
        val answers = currentQuestion.incorrect_answers.toMutableList()


        answers.add(currentQuestion.correct_answer)
        answers.shuffle()
        rd_a.text = Utils.stringCon(answers[0])
        rd_b.text = Utils.stringCon(answers[1])
        rd_c.text = Utils.stringCon(answers[2])
        rd_d.text = Utils.stringCon(answers[3])
        quid++

    }


}
