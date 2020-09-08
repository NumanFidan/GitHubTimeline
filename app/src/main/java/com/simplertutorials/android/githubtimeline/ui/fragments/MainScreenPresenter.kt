package com.simplertutorials.android.githubtimeline.ui.fragments

import com.simplertutorials.android.githubtimeline.data.api.ApiService

class MainScreenPresenter(
    private val view: SearchScreenMVP.View,
    private val apiService: ApiService
) : BasePresenter(view), MainScreenMVP.Presenter {

}