package com.simplertutorials.android.githubtimeline.ui.adapters

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.simplertutorials.android.githubtimeline.R
import com.simplertutorials.android.githubtimeline.domain.TimelineItem
import kotlinx.android.synthetic.main.timeline_recyclerview_row.view.*
import java.text.DateFormatSymbols


class TimelineAdapter(
    private val timelineList: ArrayList<TimelineItem>,
    private val rightAnimation: Animation,
    private val leftAnimation: Animation
) : RecyclerView.Adapter<TimelineAdapter.TimelineHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimelineHolder {
        context = parent.context
        val view = LayoutInflater.from(context)
            .inflate(R.layout.timeline_recyclerview_row, parent, false)
        return TimelineHolder(view)
    }

    override fun getItemCount(): Int {
        return timelineList.size
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: TimelineHolder, position: Int) {
        adjustEffectsVisibility(holder, position)
        holder.repoDate.text = visualizeDate(position)
        holder.repoTitle.text = timelineList[position].repoName
        holder.repoDescription.text = timelineList[position].repoDescription
        animateTheItem(holder)
    }

    private fun animateTheItem(holder: TimelineHolder) {
        holder.repoTextContainer.startAnimation(rightAnimation)
        holder.repoDate.startAnimation(leftAnimation)
    }


    private fun visualizeDate(position: Int): String {
        val stringBuilder = StringBuilder()
        val splittedDate = timelineList[position].repoCreatedAt
            ?.split("T")?.get(0)
            ?.split("-")

        val dayOfDate = splittedDate!!.get(2).toInt()
        stringBuilder.append(dayOfDate)
        stringBuilder.append(when (dayOfDate % 10) {
            1 -> "st"
            2 -> "nd"
            3 -> "rd"
            else -> "th"
        })
        stringBuilder.append(" ")
        val month = splittedDate!!.get(1)
        stringBuilder.append(DateFormatSymbols().getMonths()[month.toInt()-1])
        stringBuilder.append("\n")

        val year = splittedDate!!.get(0)
        stringBuilder.append(year)

        return stringBuilder.toString()
    }

    private fun adjustEffectsVisibility(holder: TimelineHolder, position: Int) {
        when {
            timelineList.size == 1 -> {
                holder.beginingEffect.visibility = View.VISIBLE
                holder.endEffect.visibility = View.VISIBLE
            }
            position == 0 -> {
                holder.beginingEffect.visibility = View.VISIBLE
                holder.endEffect.visibility = View.GONE
            }
            position + 1 == timelineList.size -> {
                holder.beginingEffect.visibility = View.GONE
                holder.endEffect.visibility = View.VISIBLE
            }
            else -> {
                holder.beginingEffect.visibility = View.GONE
                holder.endEffect.visibility = View.GONE
            }
        }
    }

    class TimelineHolder(view: View) : RecyclerView.ViewHolder(view) {
        var endEffect = view.timelinerow_endOfListEffect
        var beginingEffect = view.timelinerow_beginningOfListEffect
        var repoDate = view.timelinerow_date
        var repoTitle = view.timelinerow_title
        var repoDescription = view.timelinerow_description
        var repoTextContainer = view.timelinerow_textcontainer
    }
}