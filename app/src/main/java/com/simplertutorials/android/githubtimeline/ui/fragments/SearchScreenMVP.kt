package com.simplertutorials.android.githubtimeline.ui.fragments

import android.content.Context
import com.simplertutorials.android.githubtimeline.data.api.ApiService
import com.simplertutorials.android.githubtimeline.domain.User
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

interface SearchScreenMVP :BaseMVP{
    interface View:BaseMVP.View{
        val behaviorSubject: BehaviorSubject<List<User>>
        val suggestionsList: ArrayList<User>
        fun searchBoxObservable():Observable<String>?
        fun suggestionsRefreshed()
        fun showTextInputLayoutError(string: String)
    }

    interface Presenter: BaseMVP.Presenter{
        fun subscribeToSearchBox()
    }
}