package com.example.meowmory

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_game.*
import com.example.meowmory.R.drawable.*
import com.example.roomintro.AppDatabase
import com.example.roomintro.Item
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class GameActivity : AppCompatActivity(), CoroutineScope {

    private lateinit var job : Job
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private lateinit var db : AppDatabase

    val cardDown = purpleink
    var clicked = 0
    var turnOver = false
    var lastClicked = -1
    var totalCards = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        job = Job()

        db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "highscore")
            .fallbackToDestructiveMigration().build()

        val startTime = System.currentTimeMillis()
        Log.d("!!!", "Start time; $startTime")


        val button = findViewById<Button>(R.id.finishButton)

        val buttons = arrayOf(
            cardbg1, cardbg2, cardbg3, cardbg4, cardbg5, cardbg6,
            cardbg7, cardbg8, cardbg9, cardbg10, cardbg11, cardbg12
        )

        val images = mutableListOf(
            blackcat, calicocat, graycat, orangecat, tortiecat, whitecat,
            blackcat, calicocat, graycat, orangecat, tortiecat, whitecat
        )

        button.setOnClickListener {

            val intent = Intent(this, FinishActivity::class.java)

            startActivity(intent)
        }

        images.shuffle()

        for (i in 0..11) {
            buttons[i].text = "cardDown"
            buttons[i].textSize = 0.0F
            buttons[i].setOnClickListener {
                if (buttons[i].text == "cardDown" && !turnOver) {
                    buttons[i].setBackgroundResource(images[i])
                    buttons[i].setText(images[i])
                    if (clicked == 0) {
                        lastClicked = i
                    }
                    clicked++
                } else if (buttons[i].text !in "cardDown") {
                    buttons[i].setBackgroundResource(cardDown)
                    buttons[i].text = "cardDown"
                    clicked--
                }
                if (clicked == 2) {
                    turnOver = true
                    if (buttons[i].text == buttons[lastClicked].text) {
                        buttons[i].isClickable = false
                        buttons[lastClicked].isClickable = false
                        turnOver = false
                        clicked = 0
                        totalCards++
                        goToFinish(startTime)
                    }
                } else if (clicked == 0) {
                    turnOver = false
                }
            }
        }
    }

    fun stopTimer(startTime: Long) {
        val stopTime = System.currentTimeMillis()
        Log.d("!!!", "Stop time; $stopTime")
        val timerLength = stopTime - startTime
        Log.d("!!!", "Timer Length, $timerLength")
        val timerLengthSeconds = timerLength / 1000
        Log.d("!!!", "Timer Length Seconds, $timerLengthSeconds")
        val highscoreEntry = Item(0, timerLengthSeconds)
        saveScore(highscoreEntry)
    }

    fun saveScore(highscoreEntry: Item) {
        launch(Dispatchers.IO) {
            db.itemDao().insert(highscoreEntry)
        }
    }

    fun goToFinish(startTime: Long) {
        if (totalCards == 6) {
            val intent = Intent(this, FinishActivity::class.java)
            stopTimer(startTime)
            startActivity(intent)
        }
    }

    //val elapsed = measureTimeMillis {
      //  Thread.sleep(100)
        //println("Measuring time via measureTimeMillis")
    //}
    //assertThat(elapsed).isGreaterThanOrEqualTo(100)

}


