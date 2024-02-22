package com.example.quiz_app.adapters

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.quiz_app.R
import com.example.quiz_app.activity.QuestionActivity
import com.example.quiz_app.models.Quiz
import com.example.quiz_app.utils.IconPicker
import com.example.quiz_app.utils.colorPicker
import java.text.SimpleDateFormat
import java.util.Date

class QuizAdapter(val context: Context, var quizes: List<Quiz> ):RecyclerView.Adapter<QuizAdapter.QuizViewHolder>() {

    inner class QuizViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val textViewTittle:TextView = itemView.findViewById(R.id.quizTittle)
        val iconView:ImageView = itemView.findViewById(R.id.quizIcon)
        val cardContainer:CardView = itemView.findViewById(R.id.cardContainer)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.quiz_item,parent,false)
        return QuizViewHolder(view)
    }

    override fun getItemCount(): Int {
        return quizes.size
    }

    override fun onBindViewHolder(holder: QuizViewHolder, position: Int) {
        holder.textViewTittle.text = quizes[position].tittle
        holder.cardContainer.setBackgroundColor(Color.parseColor(colorPicker.getColor()))
        holder.iconView.setImageResource(IconPicker.getIcon())
        holder.itemView.setOnClickListener {
            Toast.makeText(context, quizes[position].tittle, Toast.LENGTH_SHORT).show()
            val intent = Intent(context, QuestionActivity::class.java)
            intent.putExtra("DATE",quizes[position].tittle)
                context.startActivity(intent)
        }
    }

}