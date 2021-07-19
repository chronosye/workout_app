package com.example.workoutapp

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.workoutapp.databinding.ItemHistoryRowBinding

class HistoryAdapter(val context: Context, val items: ArrayList<String>) :
    RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    //adapter to show all dates into Recycler view on History Activity

    class ViewHolder(binding: ItemHistoryRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val llHistoryMainItem = binding.llHistoryItemMain
        val tvItem = binding.tvItem
        val tvPosition = binding.tvPosition
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemHistoryRowBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val date = items[position]

        holder.tvPosition.text = (position+1).toString()
        holder.tvItem.text = date

        //changing color of background, so its easier to separate dates.
        if(position%2 == 0){
            holder.llHistoryMainItem.setBackground(ContextCompat.getDrawable(context,R.drawable.button_gradient))
        }else{
            holder.llHistoryMainItem.setBackground(ContextCompat.getDrawable(context,R.drawable.button_gradient_less_saturation))
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }


}
