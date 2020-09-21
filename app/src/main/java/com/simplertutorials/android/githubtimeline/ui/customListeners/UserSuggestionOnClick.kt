package com.simplertutorials.android.githubtimeline.ui.customListeners

import com.simplertutorials.android.githubtimeline.domain.SearchItem
import com.simplertutorials.android.githubtimeline.domain.User

interface UserSuggestionOnClick {
    fun onUserSuggestionClicked(searchItem: SearchItem)
}