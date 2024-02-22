package com.example.quiz_app.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.quiz_app.R
import com.example.quiz_app.models.Question

class OptionAdapter(val context: Context, val question: Question):RecyclerView.Adapter<OptionAdapter.OptionViewHolder>() {

    //hum ne data option mein show karwana h is liye list bana le gy os ki help se show karwaye gy
    private var options = listOf<String>(question.option1,question.option2,question.option3, question.option4)

    inner class OptionViewHolder(itemview:View) : RecyclerView.ViewHolder(itemview){
        val optionView= itemview.findViewById<TextView>(R.id.quizOption)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OptionViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.option_item,parent,false)
        return OptionViewHolder(view)
    }

    override fun onBindViewHolder(holder: OptionViewHolder, position: Int) {
    holder.optionView.text=options[position]
        holder.itemView.setOnClickListener {
            question.useranswer = options[position]
            notifyDataSetChanged()
        }
        if (question.useranswer == options[position]){
            holder.itemView.setBackgroundResource(R.drawable.option_selected_item_bg)
        }else{
            holder.itemView.setBackgroundResource(R.drawable.option_item_bg)
        }
    }
    override fun getItemCount(): Int {
        return options.size
    }

}