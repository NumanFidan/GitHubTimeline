package com.simplertutorials.android.githubtimeline.ui.fragments

import android.util.Log
import com.simplertutorials.android.githubtimeline.R
import io.reactivex.disposables.CompositeDisposable
import java.net.UnknownHostException

open class BasePresenter(
    private val view: BaseMVP.View
): BaseMVP.Presenter{

    var subscriptions: CompositeDisposable = CompositeDisposable()

    override fun handleServerError(e: Throwable){
        Log.w("Api call Error", e.message)
        if (e is UnknownHostException)
            view.showTopSnackBar(view.getString(R.string.communication_error))
        view.showToast(e.message)
    }
    override fun onDetach() {
        //dispose the all subscriptions to prevent app from crashing
        subscriptions.dispose()
    }
}