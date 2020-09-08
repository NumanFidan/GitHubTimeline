package com.simplertutorials.android.githubtimeline.ui.fragments.MainScreen

import android.animation.Animator
import android.animation.IntEvaluator
import android.animation.ValueAnimator
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.transition.*
import com.simplertutorials.android.githubtimeline.R
import com.simplertutorials.android.githubtimeline.ui.MainActivity
import com.simplertutorials.android.githubtimeline.ui.fragments.BaseFragment
import com.simplertutorials.android.githubtimeline.ui.fragments.SearchScreen.SearchScreenFragment
import kotlinx.android.synthetic.main.main_screen_fragment.view.*


class MainScreenFragment : BaseFragment() {
    lateinit var activity: MainActivity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.main_screen_fragment, container, false)
        updateUi(view)
        return view
    }

    private fun updateUi(view: View) {
        view.main_screen_user_btn.setOnClickListener {
            changeFragmentWithTransaction(view)
        }
    }

    private fun changeFragmentWithTransaction(view: View) {
        val details: SearchScreenFragment =
            SearchScreenFragment()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            details.setSharedElementEnterTransition(DetailsTransition())
            details.setEnterTransition(Fade())
            exitTransition = Fade()
            details.setSharedElementReturnTransition(Fade())
        }

        getActivity()!!.supportFragmentManager
            .beginTransaction()
            .addSharedElement(view.main_screen_user_btn, "sharedImage")
            .replace(R.id.container, details)
            .addToBackStack(null)
            .commit()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.activity = context as MainActivity
    }

    override fun onDetach() {
        super.onDetach()
    }

    class DetailsTransition() : TransitionSet() {
        init {
            ordering = ORDERING_TOGETHER
            addTransition(CustomTransition())
            addTransition(ChangeBounds())
            addTransition(ChangeTransform())

        }
    }

    class CustomTransition() : Transition() {
        private val PROPNAME_BACKGROUND =
            "com.example.android.customtransition:CustomTransition:alpha"

        override fun captureStartValues(transitionValues: TransitionValues) {
            transitionValues.values[PROPNAME_BACKGROUND] = 100
        }

        override fun captureEndValues(transitionValues: TransitionValues) {
            transitionValues.values[PROPNAME_BACKGROUND] = 0
        }

        private fun captureValues(transitionValues: TransitionValues) {
            val view = transitionValues.view
            transitionValues.values[PROPNAME_BACKGROUND] = view.alpha
        }

        override fun createAnimator(
            sceneRoot: ViewGroup,
            startValues: TransitionValues?,
            endValues: TransitionValues?
        ): Animator? {
            if (null == startValues || null == endValues) {
                return null
            }
            val view = endValues.view
            val animator = ValueAnimator.ofObject(
                IntEvaluator(),
                100, -10
            )
            animator.addUpdateListener { animation ->
                Log.w("Animator update ", animation.animatedValue.toString())
                val value = animation.animatedValue as Int?
                if (null != value) {
                    view.alpha = value.toFloat()
                    if (value < 0)
                        view.visibility= View.GONE
                }
            }
            return animator
        }

    }

}