package com.simplertutorials.android.githubtimeline.ui.customListeners

import com.simplertutorials.android.githubtimeline.domain.User

interface UserSuggestionOnClick {
    fun onUserSuggestionClicked(user: User)
}