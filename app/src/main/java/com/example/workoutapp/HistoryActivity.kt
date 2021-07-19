package com.example.workoutapp

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.workoutapp.databinding.ActivityHistoryBinding

class HistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHistoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        //history activity for viewing history of completed trainings

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        binding = ActivityHistoryBinding.inflate(layoutInflater)
        var view = binding.root
        setContentView(view)

        setSupportActionBar(binding.toolbarHistoryActivity)

        val actionbar = supportActionBar
        if (actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true)
            actionbar.title = "HISTORY"
        }

        binding.toolbarHistoryActivity.setNavigationOnClickListener {
            onBackPressed()
        }

        getAllDates()
    }

    private fun getAllDates() {
        //getting all data from SQLite database.
        val dbHandler = SqliteOpenHelper(this, null)
        val datesList = dbHandler.getAllDatesList()

        //if there are data of dates then show them
        if(datesList.size>0){
            binding.tvHistory.visibility = View.VISIBLE
            binding.rvHistory.visibility = View.VISIBLE
            binding.tvNoData.visibility = View.GONE

            binding.rvHistory.layoutManager = LinearLayoutManager(this)
            val historyAdapter = HistoryAdapter(this, datesList)
            binding.rvHistory.adapter = historyAdapter
        }else{
            //else show that there is no data
            binding.tvHistory.visibility = View.GONE
            binding.rvHistory.visibility = View.GONE
            binding.tvNoData.visibility = View.VISIBLE
        }
    }
}