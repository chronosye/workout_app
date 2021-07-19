package com.example.workoutapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.workoutapp.databinding.ActivityFinishBinding
import java.text.SimpleDateFormat
import java.util.*

class FinishActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFinishBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        //finish activity where user did all tasks and now app adds date when this training session is done

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finish)

        binding = ActivityFinishBinding.inflate(layoutInflater)
        var view = binding.root
        setContentView(view)

        setSupportActionBar(binding.toolbarFinishActivity)
        val actionbar = supportActionBar
        if (actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true)
        }

        binding.toolbarFinishActivity.setNavigationOnClickListener {
            onBackPressed()
        }

        binding.btnFinish.setOnClickListener {
            finish()
        }

        addDateToDatabase()
    }

    private fun addDateToDatabase() {
        //adding date to SQLite database
        val calendar = Calendar.getInstance()
        val dateTime = calendar.time

        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
        val date = sdf.format(dateTime)

        val dbHandler = SqliteOpenHelper(this, null)
        dbHandler.addDate(date)
    }
}