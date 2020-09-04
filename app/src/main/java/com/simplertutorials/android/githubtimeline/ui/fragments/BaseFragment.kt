package com.simplertutorials.android.githubtimeline.ui.fragments

import android.view.Gravity
import android.widget.FrameLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import java.lang.Exception

open class BaseFragment : Fragment(), BaseMVP.View {

    private lateinit var currentToast: Toast

    override fun showTopSnackBar(message: String?) {
        if (message == null)
            return
        var snackbar = Snackbar.make(this.view!!, message, Snackbar.LENGTH_SHORT)
        var view = snackbar.view
        var params = view.layoutParams as FrameLayout.LayoutParams
        params.gravity = Gravity.TOP
        view.layoutParams = params
        snackbar.animationMode = BaseTransientBottomBar.ANIMATION_MODE_FADE
        snackbar.show()
    }

    override fun showToast(message: String?) {
        //if there is already a toast on the screen
        //  ->simply change the text
        //if there is none-> create one

        try {
            currentToast.setText(message)
        } catch (e: Exception) {
            currentToast = Toast.makeText(context, message, Toast.LENGTH_SHORT)
        }
        currentToast.show()
    }
}