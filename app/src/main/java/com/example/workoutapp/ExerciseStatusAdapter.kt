package com.example.workoutapp

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.workoutapp.databinding.ItemExerciseStatusBinding

class ExerciseStatusAdapter(val items: ArrayList<ExerciseModel>, val context: Context) :
    RecyclerView.Adapter<ExerciseStatusAdapter.ViewHolder>() {

    //Adapter for exercises to show in recycler view on bottom of page

    class ViewHolder(private val binding: ItemExerciseStatusBinding, context: Context) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: ExerciseModel, context: Context) {

            //changing exercise circle background color depending on completion
            binding.tvItem.text = model.getId().toString()
            if (model.getIsSelected()) {
                binding.tvItem.background =
                    ContextCompat.getDrawable(context, R.drawable.item_circular_thin)
                binding.tvItem.setTextColor(Color.parseColor("#212121"))
            } else if (model.getIsCompleted()) {
                binding.tvItem.background =
                    ContextCompat.getDrawable(context, R.drawable.item_circular_accent_border)
                binding.tvItem.setTextColor(Color.WHITE)
            } else {
                binding.tvItem.background =
                    ContextCompat.getDrawable(context, R.drawable.item_circular_color_gray)
                binding.tvItem.setTextColor(Color.BLACK)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemExerciseStatusBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model: ExerciseModel = items[position]
        holder.bind(model, context)
    }

    override fun getItemCount(): Int {
        return items.size
    }
}