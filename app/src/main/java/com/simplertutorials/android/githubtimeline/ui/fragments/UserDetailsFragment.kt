package com.simplertutorials.android.githubtimeline.ui.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.simplertutorials.android.githubtimeline.MainApplication
import com.simplertutorials.android.githubtimeline.R
import com.simplertutorials.android.githubtimeline.data.api.ApiService
import com.simplertutorials.android.githubtimeline.domain.DetailedUser
import com.simplertutorials.android.githubtimeline.domain.TimelineItem
import com.simplertutorials.android.githubtimeline.domain.User
import com.simplertutorials.android.githubtimeline.ui.MainActivity
import com.simplertutorials.android.githubtimeline.ui.adapters.TimelineAdapter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.user_details_screen.view.*
import javax.inject.Inject

class UserDetailsFragment : BaseFragment() {

    private lateinit var _presenter: UserDetailsPresenter
    private lateinit var activity: MainActivity
    private lateinit var recyclerViewAdapter: TimelineAdapter
    lateinit var timelineList: ArrayList<TimelineItem>
    private val ARG_USER_PARAM: String = "current_user"
    private lateinit var currentUser: User

    private lateinit var swipeToRefreshLayout: SwipeRefreshLayout
    private lateinit var userAvatar: ImageView
    private lateinit var userName: TextView
    private lateinit var loginName: TextView
    private lateinit var userLocation: TextView
    private lateinit var userSite: TextView
    private lateinit var userFollowerCount: TextView
    private lateinit var userFollowingCount: TextView
    private lateinit var userLocationLayout: ConstraintLayout
    private lateinit var userSiteLayout: ConstraintLayout

    @Inject
    lateinit var apiService: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (arguments != null) {
            currentUser = arguments!!.getParcelable(ARG_USER_PARAM)!!
        }

        (activity.applicationContext as MainApplication).component.inject(this)
        _presenter = UserDetailsPresenter(this, apiService)
        timelineList = ArrayList()
        _presenter.getUserDetails(currentUser)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.user_details_screen, container, false)
        updateUi(view)
        return view
    }

    private fun updateUi(view: View) {
        //show loading until response from API come
        //initialize all views
        setUpSwipeToRefresh(view)
        showLoading()

        setUpRecyclerView(view.details_timelinerecyclerview)

        userAvatar = view.details_useravatar
        userName = view.details_username
        loginName = view.details_loginname
        userLocation = view.details_userlocation
        userLocationLayout = view.details_locationlayout
        userSite = view.details_usersite
        userSiteLayout = view.details_sitelayout
        userFollowerCount = view.details_userfollowercount
        userFollowingCount = view.details_userfollowingcount
    }

    private fun setUpSwipeToRefresh(view: View) {
        //get User details or reload all data on Swipe to Refresh
        swipeToRefreshLayout = view.swipetorefresh_layout
        swipeToRefreshLayout.setOnRefreshListener {
            _presenter.getUserDetails(currentUser)
        }
    }

    @SuppressLint("ResourceType")
    private fun setUpRecyclerView(timeline: RecyclerView?) {
        //Set Up Recycler View and pass animations for Items in recyclerView
        val rightAnimation: Animation =
            AnimationUtils.loadAnimation(
                activity, R.animator.animation_right_fade_in)
        val leftAnimation: Animation =
            AnimationUtils.loadAnimation(
                activity, R.animator.animation_left_fade_in)

        recyclerViewAdapter = TimelineAdapter(
            timelineList,
            rightAnimation,
            leftAnimation
        )

        val layoutManager = LinearLayoutManager(context)
        timeline?.apply {
            setHasFixedSize(true)
            adapter = recyclerViewAdapter
            this.layoutManager = layoutManager
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.activity = context as MainActivity
    }

    override fun onDetach() {
        super.onDetach()
        this._presenter.onDetach()
    }

    fun fillUserDetails(user: DetailedUser?) {
        //fill all user data to the relevant views
        if (user == null)
            return

        userName.text = user.userName ?: ""
        loginName.text = user.loginName ?: ""
        Picasso.with(context).load(user.avatarUrl).into(userAvatar)
        if (user.location != null)
            userLocation.text = user.location
        else
            userLocationLayout.visibility = View.GONE

        if (user.siteUrl != null)
            userSite.text = user.siteUrl
        else
            userSiteLayout.visibility = View.GONE

        userFollowerCount.text = user.followerCount.toString()
        userFollowingCount.text = user.followingCount.toString()

        hideLoading()
    }

    fun refreshTimelineRecyclerView() {
        recyclerViewAdapter.notifyDataSetChanged()
    }

    fun showLoading() {
        swipeToRefreshLayout.isRefreshing = true
    }

    fun hideLoading() {
        swipeToRefreshLayout.isRefreshing = false
    }


}