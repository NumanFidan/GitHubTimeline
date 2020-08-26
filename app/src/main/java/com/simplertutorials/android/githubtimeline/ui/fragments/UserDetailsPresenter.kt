package com.simplertutorials.android.githubtimeline.ui.fragments

import android.util.Log
import com.simplertutorials.android.githubtimeline.R
import com.simplertutorials.android.githubtimeline.data.api.ApiRepository
import com.simplertutorials.android.githubtimeline.data.api.ApiService
import com.simplertutorials.android.githubtimeline.domain.DetailedUser
import com.simplertutorials.android.githubtimeline.domain.TimelineItem
import com.simplertutorials.android.githubtimeline.domain.User
import io.reactivex.disposables.CompositeDisposable
import java.net.UnknownHostException
import java.util.ArrayList

class UserDetailsPresenter(
    private val view: UserDetailsFragment,
    private val apiService: ApiService
) : BasePresenter(view) {

    fun getUserDetails(currentUser: User) {
        //get User Data from API
        //at the end call API again to get user's repo list
        val disposable = ApiRepository.getInstance()
            .getUser(apiService, currentUser.loginName)
            .subscribe({ n ->
                view.fillUserDetails(n)
                getUserTimeline(n)
            },
                { e ->
                    handleServerError(e)
                    view.hideLoading()
                })
        subscriptions.add(disposable)
    }

    private fun getUserTimeline(n: DetailedUser?) {
        //get user repo list from API
        //in the end sort the list by date and notify the view about data change
        view.showLoading()
        view.timelineList.clear()
        val disposable = ApiRepository.getInstance()
            .getUserTimeline(apiService, n?.loginName)
            .subscribe({ n ->
                view.timelineList.addAll(n)
                sortList(view.timelineList)
                view.refreshTimelineRecyclerView()
                view.hideLoading()
            },
                { e ->
                    handleServerError(e)
                    view.hideLoading()
                })
        subscriptions.add(disposable)
    }

    private fun sortList(timelineList: ArrayList<TimelineItem>) {
        //sort the list by repo creating date
        val orderedList = quickSort(timelineList)
        timelineList.clear()
        timelineList.addAll(orderedList)
    }

    private fun quickSort(items: List<TimelineItem>): List<TimelineItem> {
        //user Quick sort method to sort the list
        if (items.size < 2)
            return items

        val pivot = items[items.size / 2]

        val equals = items.filter { it == pivot }
        val less = items.filter { it < pivot }
        val greater = items.filter { it > pivot }
        return quickSort(greater) + equals + quickSort(less)
    }
}