package com.example.workoutapp

import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.workoutapp.databinding.ActivityExerciseBinding
import com.example.workoutapp.databinding.DialogCustomBackBinding
import java.util.*

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private lateinit var binding: ActivityExerciseBinding
    private var restTimer: CountDownTimer? = null
    private var restProgress = 0

    private var exerciseTimer: CountDownTimer? = null
    private var exerciseProgress = 0

    private var exerciseList: ArrayList<ExerciseModel>? = null
    private var currentExercisePosition = -1

    private var tts: TextToSpeech? = null
    private var player: MediaPlayer? = null
    private var exerciseAdapter: ExerciseStatusAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise)

        binding = ActivityExerciseBinding.inflate(layoutInflater)
        var view = binding.root
        setContentView(view)

        setSupportActionBar(binding.toolbarExerciseActivity)
        val actionbar = supportActionBar
        if (actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true)
        }
        binding.toolbarExerciseActivity.setNavigationOnClickListener {
            customDialogForBackButton()
        }

        //creating textToSpeech object
        tts = TextToSpeech(this, this)

        //creating exercise list to show on bottom off page
        exerciseList = Constants.defaultExerciseList()

        //creating view after presses start
        setUpRestView()

        //setting up bottom part of view, where are list of exercises
        setUpExerciseStatusRecyclerView()

    }

    private fun setUpRestView() {
        //setting up rest view, when user presses start and after every exercise
        try {
            //playing start melody
            player = MediaPlayer.create(applicationContext, R.raw.press_start)
            player!!.isLooping = false
            player!!.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        binding.llRestView.visibility = View.VISIBLE
        binding.llExerciseView.visibility = View.GONE

        if (restTimer != null) {
            restTimer!!.cancel()
            restProgress = 0
        }
        binding.tvUpcomingExercise.text = exerciseList!![currentExercisePosition + 1].getName()
        setRestProgressBar()

    }

    private fun setRestProgressBar() {
        //setting progress bar and setting up timer
        binding.progressBar.progress = restProgress
        restTimer = object : CountDownTimer(11000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                binding.progressBar.progress = 11 - restProgress
                binding.tvTimer.text = (11 - restProgress).toString()
            }

            override fun onFinish() {
                currentExercisePosition++
                exerciseList!![currentExercisePosition].setIsSelected(true)
                exerciseAdapter!!.notifyDataSetChanged()
                setUpExerciseView()
            }
        }.start()
    }

    private fun setUpExerciseView() {
        //setting up exercise view
        binding.llRestView.visibility = View.GONE
        binding.llExerciseView.visibility = View.VISIBLE

        if (exerciseTimer != null) {
            exerciseTimer!!.cancel()
            exerciseProgress = 0
        }
        //giving textToSpeech text to say out loud
        speakOut(exerciseList!![currentExercisePosition].getName())
        setExerciseProgressBar()

        binding.ivImage.setImageResource(exerciseList!![currentExercisePosition].getImage())
        binding.tvExerciseName.text = exerciseList!![currentExercisePosition].getName()
    }

    private fun setExerciseProgressBar() {
        binding.progressBarExercise.progress = exerciseProgress
        exerciseTimer = object : CountDownTimer(31000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                exerciseProgress++
                binding.progressBarExercise.progress = 31 - exerciseProgress
                binding.tvExerciseTimer.text = (31 - exerciseProgress).toString()
            }

            override fun onFinish() {
                if (currentExercisePosition < exerciseList?.size!! - 1) {
                    exerciseList!![currentExercisePosition].setIsSelected(false)
                    exerciseList!![currentExercisePosition].setIsCompleted(true)
                    exerciseAdapter!!.notifyDataSetChanged()
                    setUpRestView()
                } else {
                    finish()
                    val intent = Intent(this@ExerciseActivity, FinishActivity::class.java)
                    startActivity(intent)
                }
            }
        }.start()
    }

    override fun onInit(status: Int) {
        //initializing textToSpeech object
        if (status == TextToSpeech.SUCCESS) {
            val result = tts!!.setLanguage(Locale.US)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.d("TTS", "Language not supported")
            }
        } else {
            Log.e("TTS", "Initialization failed")
        }
    }

    override fun onDestroy() {
        //checking if timers are closed, if not, then reset them.
        if (restTimer != null) {
            restTimer!!.cancel()
            restProgress = 0
        }

        if (exerciseTimer != null) {
            exerciseTimer!!.cancel()
            exerciseProgress = 0
        }

        if (tts != null) {
            tts!!.stop()
            tts!!.shutdown()
        }

        if (player != null) {
            player!!.stop()
        }

        super.onDestroy()
    }

    private fun speakOut(text: String) {
        tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }

    private fun setUpExerciseStatusRecyclerView() {
        binding.rvExerciseStatus.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        exerciseAdapter = ExerciseStatusAdapter(exerciseList!!, this)
        binding.rvExerciseStatus.adapter = exerciseAdapter
    }

    private fun customDialogForBackButton() {
        //custom dialog, if user wants to go back when he is in exercise
        val customDialog = Dialog(this@ExerciseActivity)

        val binding: DialogCustomBackBinding = DialogCustomBackBinding.inflate(layoutInflater)

        customDialog.setContentView(binding.root)


        binding.btnYes.setOnClickListener {
            finish()
            customDialog.dismiss()
        }

        binding.btnNo.setOnClickListener {
            customDialog.dismiss()
        }

        customDialog.show()
    }
}