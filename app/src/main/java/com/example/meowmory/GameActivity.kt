package com.example.meowmory

import android.app.AlertDialog
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_game.*
import com.example.meowmory.R.drawable.*

class GameActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        val images: MutableList<Int> = mutableListOf(blackcat, calicocat, graycat, orangecat, tortiecat, whitecat,
            blackcat, calicocat, graycat, orangecat, tortiecat, whitecat)

        val buttons = arrayOf(cardbg1, cardbg2, cardbg3, cardbg4, cardbg5, cardbg6,
            cardbg7, cardbg8, cardbg9, cardbg10, cardbg11, cardbg12)

        val cardDown = purpleink
        var clicked = 0
        var turnOver = false
        var lastClicked = -1

        // val alertDialogBuilder = AlertDialog.Builder(this)

        images.shuffle()
        for(i in 0..11) {
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
                }
                } else if (clicked == 0) {
                turnOver = false
            }

            }
        }
    }
}