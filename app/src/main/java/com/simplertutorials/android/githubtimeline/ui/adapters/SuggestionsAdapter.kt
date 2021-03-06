package com.simplertutorials.android.githubtimeline.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.simplertutorials.android.githubtimeline.R
import com.simplertutorials.android.githubtimeline.domain.SearchItem
import com.simplertutorials.android.githubtimeline.ui.customListeners.UserSuggestionOnClick
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.search_screen_search_suggestions_row.view.*

class SuggestionsAdapter(
    private val suggestionsList: ArrayList<SearchItem>,
    private val userSuggestionOnClick: UserSuggestionOnClick
) : RecyclerView.Adapter<SuggestionsAdapter.SuggestionsHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuggestionsHolder {
        context = parent.context
        val view = LayoutInflater.from(context)
            .inflate(R.layout.search_screen_search_suggestions_row, parent, false)
        return SuggestionsHolder(view)
    }

    override fun getItemCount(): Int {
        return suggestionsList.size
    }

    override fun onBindViewHolder(holder: SuggestionsHolder, position: Int) {
        val searchItem = suggestionsList[position]
        Picasso.with(context)
            .load(searchItem.avatarUrl)
            .into(holder.avatar)
        holder.username.text = searchItem.name
        holder.userId.text = searchItem.description.toString()
        holder.layout.setOnClickListener { userSuggestionOnClick.onUserSuggestionClicked(searchItem) }
    }

    class SuggestionsHolder(view: View) : RecyclerView.ViewHolder(view) {
        val avatar = view.suggestionsUserAvatar as ImageView
        val username = view.suggestionsUserName as TextView
        val userId = view.suggestionsUserId as TextView
        val layout = view
    }
}