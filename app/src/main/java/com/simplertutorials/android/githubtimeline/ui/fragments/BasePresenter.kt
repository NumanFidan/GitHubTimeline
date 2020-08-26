package com.simplertutorials.android.githubtimeline.ui.fragments

import android.util.Log
import com.simplertutorials.android.githubtimeline.R
import com.simplertutorials.android.githubtimeline.data.api.ApiService
import io.reactivex.disposables.CompositeDisposable
import java.net.UnknownHostException

open class BasePresenter (
    private val view: BaseFragment){

    var subscriptions: CompositeDisposable = CompositeDisposable()

    fun handleServerError(e: Throwable){
        Log.w("Api call Error", e.message)
        if (e is UnknownHostException)
            view.showTopSnackBar(view.context?.getString(R.string.communication_error))
        view.showToast(e.message)
    }
    fun onDetach() {
        //dispose the all subscriptions to prevent app from crashing
        subscriptions.dispose()
    }
}