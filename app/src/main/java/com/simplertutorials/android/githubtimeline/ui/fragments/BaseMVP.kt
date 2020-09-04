package com.simplertutorials.android.githubtimeline.ui.fragments

interface BaseMVP {
    interface View{
        fun showTopSnackBar(message: String?)
        fun showToast(message: String?)
        fun getString(id: Int): String?
    }
    interface Presenter{

        fun handleServerError(e: Throwable)
        fun onDetach()
    }
}