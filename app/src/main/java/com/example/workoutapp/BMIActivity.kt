package com.example.workoutapp

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.workoutapp.databinding.ActivityBmiactivityBinding
import java.math.BigDecimal
import java.math.RoundingMode


//BMI calculator activity
class BMIActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBmiactivityBinding
    val METRIC_UNITS_VIEW = "METRIC_UNIT_VIEW"
    val US_UNITS_VIEW = "US_UNIT_VIEW"

    var currentVisibleView = METRIC_UNITS_VIEW

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmiactivity)

        binding = ActivityBmiactivityBinding.inflate(layoutInflater)
        var view = binding.root
        setContentView(view)

        setSupportActionBar(binding.toolbarBmiActivity)

        //adding action bar on top of activity
        val actionbar = supportActionBar
        if (actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true)
            actionbar.title = "Calculate BMI"
        }

        binding.toolbarBmiActivity.setNavigationOnClickListener {
            onBackPressed()
        }

        //calculating BMI
        binding.btnCalculateBmi.setOnClickListener {
            if (currentVisibleView.equals(METRIC_UNITS_VIEW)) { //checking what units are selected
                if (validateMetricUnits()) {
                    val height = binding.etMetricHeight.text.toString().toFloat() / 100
                    val weight = binding.etMetricWeight.text.toString().toFloat()

                    val bmi = weight / (height * height)
                    displayBMIResult(bmi)
                } else {
                    Toast.makeText(this, "Please enter valid values!", Toast.LENGTH_SHORT).show()
                }
            } else {
                if (validateUsUnits()) {
                    val heightIn = binding.etUsHeightIn.text.toString().toFloat()
                    val heightFt = binding.etUsHeightFt.text.toString().toFloat()
                    val weight = binding.etUsWeight.text.toString().toFloat()

                    val height = heightIn + heightFt * 12

                    val bmi = 703 * (weight / (height * height))
                    displayBMIResult(bmi)
                } else {
                    Toast.makeText(this, "Please enter valid values!", Toast.LENGTH_SHORT).show()
                }
            }

        }

        makeVisibleMetricUnitsView() //selecting default view
        //if radio group is selected, change views
        binding.rgUnits.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == R.id.rb_metric_units) {
                makeVisibleMetricUnitsView()
            } else {
                makeVisibleUSUnitsView()
            }
        }
    }

    private fun validateMetricUnits(): Boolean {

        var isValid = true

        when {
            binding.etMetricWeight.text.toString().isEmpty() -> isValid = false
            binding.etMetricHeight.text.toString().isEmpty() -> isValid = false
        }

        return isValid
    }

    private fun validateUsUnits(): Boolean {

        var isValid = true

        when {
            binding.etUsWeight.text.toString().isEmpty() -> isValid = false
            binding.etUsHeightFt.text.toString().isEmpty() -> isValid = false
            binding.etUsHeightIn.text.toString().isEmpty() -> isValid = false
        }

        return isValid
    }

    private fun makeVisibleMetricUnitsView() {
        currentVisibleView = METRIC_UNITS_VIEW

        binding.tilMetricWeight.visibility = View.VISIBLE
        binding.tilMetricHeight.visibility = View.VISIBLE

        binding.tilUsWeight.visibility = View.GONE
        binding.llUsHeight.visibility = View.GONE

        binding.llDisplayBmiResult.visibility = View.INVISIBLE

        binding.etMetricWeight.text!!.clear()
        binding.etMetricHeight.text!!.clear()
    }

    private fun makeVisibleUSUnitsView() {
        currentVisibleView = US_UNITS_VIEW

        binding.tilMetricWeight.visibility = View.GONE
        binding.tilMetricHeight.visibility = View.GONE

        binding.tilUsWeight.visibility = View.VISIBLE
        binding.llUsHeight.visibility = View.VISIBLE

        binding.llDisplayBmiResult.visibility = View.INVISIBLE

        binding.etUsWeight.text!!.clear()
        binding.etUsHeightFt.text!!.clear()
        binding.etUsHeightIn.text!!.clear()
    }

    private fun displayBMIResult(bmi: Float) {
        val bmiLabel: String
        val bmiDescription: String


        if (bmi.compareTo(15f) <= 0) {
            bmiLabel = "Very severely underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(15f) > 0 && bmi.compareTo(16f) <= 0
        ) {
            bmiLabel = "Severely underweight"
            bmiDescription = "Oops!You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(16f) > 0 && bmi.compareTo(18.5f) <= 0
        ) {
            bmiLabel = "Underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(18.5f) > 0 && bmi.compareTo(25f) <= 0
        ) {
            bmiLabel = "Normal"
            bmiDescription = "Congratulations! You are in a good shape!"
        } else if (bmi.compareTo(25f) > 0 && bmi.compareTo(30f) <= 0
        ) {
            bmiLabel = "Overweight"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
        } else if (bmi.compareTo(30f) > 0 && bmi.compareTo(35f) <= 0
        ) {
            bmiLabel = "Obese Class | (Moderately obese)"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
        } else if (bmi.compareTo(35f) > 0 && bmi.compareTo(40f) <= 0
        ) {
            bmiLabel = "Obese Class || (Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        } else {
            bmiLabel = "Obese Class ||| (Very Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        }

        binding.llDisplayBmiResult.visibility = View.VISIBLE

        val bmiValue = BigDecimal(bmi.toDouble()).setScale(2, RoundingMode.HALF_EVEN).toString()

        binding.tvBmiValue.text = bmiValue
        binding.tvBmiDescription.text = bmiDescription
        binding.tvBmiType.text = bmiLabel

    }
}