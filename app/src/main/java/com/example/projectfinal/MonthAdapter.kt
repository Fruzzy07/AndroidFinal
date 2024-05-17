package com.example.projectfinal

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class MonthAdapter(
    private val context: Context,
    private val monthsGone: Int,
    private val totalMonths: Int,
    private val detailActivityLauncher: ActivityResultLauncher<Intent>
) : RecyclerView.Adapter<MonthAdapter.MonthViewHolder>() {
    private val months = arrayOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec")
    private val monthStatuses = Array(totalMonths) { "DEFAULT" }

//    class MonthViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val monthView: View = itemView.findViewById(R.id.monthView)
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MonthViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_month, parent, false)
        return MonthViewHolder(view)
    }

    override fun onBindViewHolder(holder: MonthViewHolder, position: Int) {
        holder.itemView.setBackgroundColor(getColorForStatus(monthStatuses[position]))

        holder.itemView.setOnClickListener {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("MONTH_INDEX", position)
            detailActivityLauncher.launch(intent)
        }

        val monthView = holder.monthView
        val monthNumber = position + 1
        val month = months[position % 12]
        holder.monthLabel.text = month
        if (position < monthsGone) {
            monthView.setBackgroundColor(ContextCompat.getColor(context, android.R.color.darker_gray))
        } else {
            monthView.setBackgroundColor(ContextCompat.getColor(context, android.R.color.white))
        }

        // Проверка, является ли текущий элемент первым в строке
        val isFirstInRow = position % 12 == 0
        if (isFirstInRow) {
            holder.rowNumber.visibility = View.VISIBLE
            holder.rowNumber.text = (position / 12).toString()
        } else {
            holder.rowNumber.visibility = View.INVISIBLE
        }
        // Adding margin after every 4th element in each row (except the last row)
        val layoutParams = monthView.layoutParams as ViewGroup.MarginLayoutParams
        if (position % 12 >= 4 && position % 12 < 8) {
            layoutParams.bottomMargin = 16 // Adding bottom margin after every 4th square
        } else {
            layoutParams.bottomMargin = 0 // Reset margin for other squares
        }
        monthView.layoutParams = layoutParams
    }

    override fun getItemCount(): Int {
        return totalMonths
    }

    inner class MonthViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val monthLabel: TextView = itemView.findViewById(R.id.monthLabel)
        val monthView: View = itemView.findViewById(R.id.monthView)
        val rowNumber: TextView = itemView.findViewById(R.id.rowNumber)
    }


    fun updateMonthStatus(monthIndex: Int, status: String) {
        monthStatuses[monthIndex] = status
        notifyItemChanged(monthIndex)
    }

    private fun getColorForStatus(status: String): Int {
        return when (status) {
            "GOOD" -> Color.GREEN
            "OK" -> Color.YELLOW
            "BAD" -> Color.RED
            else -> Color.GRAY
        }
    }

}