package com.simplertutorials.android.githubtimeline.ui.fragments.searchScreen

import com.simplertutorials.android.githubtimeline.domain.SearchItem
import com.simplertutorials.android.githubtimeline.domain.User
import com.simplertutorials.android.githubtimeline.ui.fragments.BaseMVP
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

interface SearchScreenMVP :
    BaseMVP {
    interface View:
        BaseMVP.View {
        val behaviorSubject: BehaviorSubject<List<SearchItem>>
        val suggestionsList: ArrayList<SearchItem>
        fun searchBoxObservable():Observable<String>?
        fun suggestionsRefreshed()
        fun showTextInputLayoutError(string: String)
    }

    interface Presenter:
        BaseMVP.Presenter {
        fun subscribeToSearchBox()
        fun writeUserToRealm(searchItem: SearchItem)
    }
}