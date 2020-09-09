package com.simplertutorials.android.githubtimeline.ui.fragments.mainScreen

import com.simplertutorials.android.githubtimeline.data.api.ApiService
import com.simplertutorials.android.githubtimeline.ui.fragments.BasePresenter
import com.simplertutorials.android.githubtimeline.ui.fragments.searchScreen.SearchScreenMVP

class MainScreenPresenter(
    private val view: SearchScreenMVP.View,
    private val apiService: ApiService
) : BasePresenter(view), MainScreenMVP.Presenter {

}