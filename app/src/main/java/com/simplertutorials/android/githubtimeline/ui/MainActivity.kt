package com.simplertutorials.android.githubtimeline.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.simplertutorials.android.githubtimeline.MainApplication
import com.simplertutorials.android.githubtimeline.R
import com.simplertutorials.android.githubtimeline.data.api.ApiService
import com.simplertutorials.android.githubtimeline.domain.User
import com.simplertutorials.android.githubtimeline.ui.fragments.mainScreen.MainScreenFragment
import com.simplertutorials.android.githubtimeline.ui.fragments.searchScreen.SearchScreenFragment
import com.simplertutorials.android.githubtimeline.ui.fragments.userDetails.UserDetailsFragment
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var apiService: ApiService

    private val ARG_USER_PARAM: String = "current_user"

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        (applicationContext as MainApplication).component.inject(this)
//        loadDetailsFragment()
//        loadSearchFragment()
        loadMainScreenFragment()
    }

    private fun loadMainScreenFragment() {

        this.changeFragment(R.id.container,
            MainScreenFragment()
        )
    }

    private fun loadDetailsFragment() {
        val fragment =
            UserDetailsFragment()
        val args = Bundle()
        val user = User()
        user.loginName = "foo"
        args.putParcelable(ARG_USER_PARAM, user)
        fragment.arguments = args
        this.changeFragment(R.id.container, fragment)
    }

    private fun loadSearchFragment() {
        changeFragment(R.id.container,
            SearchScreenFragment()
        )
    }

    fun changeFragment(containerId: Int, fragment: Fragment) {
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(containerId, fragment)
        ft.commit()
    }

    override fun onBackPressed() {
        //if user at details Screen than return the user to the search scree
        //in other cases let Activity handle it by itself
        val fragment = supportFragmentManager.findFragmentById(R.id.container)

        when (fragment) {
            is UserDetailsFragment -> changeFragment(R.id.container,
                SearchScreenFragment()
            )
            is SearchScreenFragment -> changeFragment(R.id.container,
                MainScreenFragment()
            )
            else -> super.onBackPressed()
        }
    }
}