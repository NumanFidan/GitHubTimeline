package com.simplertutorials.android.githubtimeline.ui.fragments

import android.util.Log
import com.simplertutorials.android.githubtimeline.R
import com.simplertutorials.android.githubtimeline.data.api.ApiRepository
import com.simplertutorials.android.githubtimeline.data.api.ApiService
import com.simplertutorials.android.githubtimeline.data.dataHolders.DataRepository
import com.simplertutorials.android.githubtimeline.domain.User
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import java.util.ArrayList
import java.util.concurrent.TimeUnit

class SearchScreenPresenter(
    private val view: SearchScreenFragment,
    private val apiService: ApiService
) : BasePresenter(view) {

    fun subscribeToSearchBox() {
        //subscribe to the Search Box to get all typing emits
        var disposable = view.searchBoxObservable()
            ?.debounce(500, TimeUnit.MILLISECONDS) //Prevent all text changes to make an Api call
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe({ n ->
                callApi(n)
            }, { e ->
                Log.w("Error on SearchBox", e.message)
                view.showToast(e.message)
            })
        if (disposable != null) {
            subscriptions.add(disposable)
        }
    }

    private fun callApi(n: String) {
        //call api and get suggestions
        //prevent api call with empty text
        if (n == "") {
            DataRepository.getInstance().currentSuggestions.clear()
            view.suggestionsRefreshed()
        } else {
            var apiDisposable = ApiRepository.getInstance()
                .searchUser(apiService, n)
                .subscribe({ n ->
                    //save all suggestions and notify suggestions recycler view
                    if (n.users != null) {
                        DataRepository.getInstance().currentSuggestions.clear()
                        n.users.let {
                            DataRepository.getInstance().currentSuggestions.addAll(it)
                            view.suggestionsRefreshed()
                        }
                        if (n.users.isEmpty())
                            view.showTextInputLayoutError(view.getString(R.string.no_user_found))
                    }
                }, { e ->
                    this.handleServerError(e)
                    DataRepository.getInstance().currentSuggestions.clear()
                    view.suggestionsRefreshed()
                })
            subscriptions.add(apiDisposable)
        }
    }

    fun updateSuggestionsList(suggestionsList: ArrayList<User>) {
        //delete all suggestions from previous API calls and add all new ones
        suggestionsList.clear()
        suggestionsList.addAll(DataRepository.getInstance().currentSuggestions)
    }

}
