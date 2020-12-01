package com.example.meowmory

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SortedList
import androidx.room.Room
import com.example.roomintro.AppDatabase
import com.example.roomintro.Item
import kotlinx.android.synthetic.main.activity_finish.*
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class FinishActivity : AppCompatActivity(), CoroutineScope {

    private lateinit var job : Job
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private lateinit var db : AppDatabase

    lateinit var highscoreRecyclerView : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finish)

        job = Job()

        db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "highscore")
            .fallbackToDestructiveMigration().build()

        val loadedHighscore = getData()
        Log.d("!!!","loadedHighscore : $loadedHighscore")

        launch(Dispatchers.Main) {
            val itemsList = loadedHighscore.await()
            for(item in itemsList) {
                Log.d("!!!", "item, $item")
                DataManager.items.add(item)
            }
            highscoreRecyclerView.adapter?.notifyDataSetChanged()
        }

        highscoreRecyclerView = findViewById<RecyclerView>(R.id.highscoreRecyclerView)
        highscoreRecyclerView.layoutManager = LinearLayoutManager(this)
        highscoreRecyclerView.adapter = HighscoreRecyclerAdapter(this, DataManager.items)

        val button = findViewById<Button>(R.id.newGame)
        button.setOnClickListener {

            val intent = Intent(this, GameActivity::class.java)
            DataManager.items.clear()
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        highscoreRecyclerView.adapter?.notifyDataSetChanged()
    }

    fun getData () : Deferred<List<Item>> =
        async(Dispatchers.IO) {
            db.itemDao().getAll()
    }
}