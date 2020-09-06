package com.simplertutorials.android.githubtimeline.ui.fragments

import com.simplertutorials.android.githubtimeline.domain.DetailedUser
import com.simplertutorials.android.githubtimeline.domain.TimelineItem
import com.simplertutorials.android.githubtimeline.domain.User
import io.reactivex.subjects.BehaviorSubject

interface UserDetailsMVP : BaseMVP {
    interface View : BaseMVP.View {
        val timelineList: ArrayList<TimelineItem>

        fun fillUserDetails(detailedUser: DetailedUser?)
        fun hideLoading()
        fun showLoading()
        fun refreshTimelineRecyclerView()


    }

    interface Presenter : BaseMVP.Presenter {
        fun getUserDetails(currentUser: User)
    }
}