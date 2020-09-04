package com.simplertutorials.android.githubtimeline.ui.fragments

import com.simplertutorials.android.githubtimeline.R
import com.simplertutorials.android.githubtimeline.data.api.ApiRepository
import com.simplertutorials.android.githubtimeline.data.api.ApiService
import com.simplertutorials.android.githubtimeline.domain.User
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

class SearchScreenPresenter(
    private val view: SearchScreenMVP.View,
    private val apiService: ApiService
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


    @Throws(Exception::class)
    fun callApi(n: String) {
        val emptyUserlist: List<User> = listOf()
        //call api and get suggestions
        //prevent api call with empty text
        if (n == "") {
            view.behaviorSubject.onNext(emptyUserlist)
            view.suggestionsRefreshed()
        } else {
            var apiDisposable = ApiRepository.getInstance()
                .searchUser(apiService, n)
                .subscribe({ n ->
                    //save all suggestions and notify suggestions recycler view
                    n.users.let {
                        view.behaviorSubject.onNext(n.users)
                        view.suggestionsRefreshed()
                    }

                    if (n.users.isEmpty())
                        view.showTextInputLayoutError(view.getString(R.string.no_user_found).toString())
                }, { e ->
                    this.handleServerError(e)
                    view.behaviorSubject.onNext(emptyUserlist)
                    view.suggestionsRefreshed()

                })
            subscriptions.add(apiDisposable)
        }
    }


}
