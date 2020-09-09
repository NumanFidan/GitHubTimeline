package com.simplertutorials.android.githubtimeline.ui.fragments.searchScreen

import com.simplertutorials.android.githubtimeline.domain.User
import com.simplertutorials.android.githubtimeline.ui.fragments.BaseMVP
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

interface SearchScreenMVP :
    BaseMVP {
    interface View:
        BaseMVP.View {
        val behaviorSubject: BehaviorSubject<List<User>>
        val suggestionsList: ArrayList<User>
        fun searchBoxObservable():Observable<String>?
        fun suggestionsRefreshed()
        fun showTextInputLayoutError(string: String)
    }

    interface Presenter:
        BaseMVP.Presenter {
        fun subscribeToSearchBox()
    }
}