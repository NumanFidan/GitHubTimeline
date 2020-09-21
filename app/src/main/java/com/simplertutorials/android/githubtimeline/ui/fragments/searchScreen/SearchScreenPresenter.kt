package com.simplertutorials.android.githubtimeline.ui.fragments.searchScreen

import com.simplertutorials.android.githubtimeline.R
import com.simplertutorials.android.githubtimeline.data.api.ApiRepository
import com.simplertutorials.android.githubtimeline.data.local.RealmRepository
import com.simplertutorials.android.githubtimeline.domain.SearchItem
import com.simplertutorials.android.githubtimeline.ui.fragments.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

class SearchScreenPresenter(
    private val view: SearchScreenMVP.View,
    private val apiRepository: ApiRepository,
    private val realmRepository: RealmRepository
) : BasePresenter(view), SearchScreenMVP.Presenter {
    override fun subscribeToSearchBox() {
        //subscribe to the Search Box to get all typing emits
        var disposable = view.searchBoxObservable()
            ?.debounce(500, TimeUnit.MILLISECONDS) //Prevent all text changes make an Api call
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe({ n ->
                callApi(n)
            }, { e ->
                view.showToast(e.message)
            })
        if (disposable != null) {
            subscriptions.add(disposable)
        }
    }

    override fun writeUserToRealm(searchItem: SearchItem) {
        realmRepository.writeToRecentSearches(searchItem)
    }

    @Throws(Exception::class)
    fun callApi(n: String) {
        val emptySearchList: List<SearchItem> = listOf()
        //call api and get suggestions
        //prevent api call with empty text
        if (n == "") {
            view.behaviorSubject.onNext(emptySearchList)
            view.suggestionsRefreshed()
        } else {
            var apiDisposable = apiRepository
                .searchUser(n)
                .subscribe({ recentSearchItemList ->
                    //save all suggestions and notify suggestions recycler view
                    recentSearchItemList.let {
                        view.behaviorSubject.onNext(recentSearchItemList)
                        view.suggestionsRefreshed()
                    }

                    if (recentSearchItemList.isEmpty())
                        view.showTextInputLayoutError(view.getString(R.string.no_user_found).toString())
                }, { e ->
                    this.handleServerError(e)
                    view.behaviorSubject.onNext(emptySearchList)
                    view.suggestionsRefreshed()

                })
            subscriptions.add(apiDisposable)
        }
    }


}
