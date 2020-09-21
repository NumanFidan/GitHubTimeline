package com.simplertutorials.android.githubtimeline.ui.fragments.userDetails

import com.simplertutorials.android.githubtimeline.data.api.ApiRepository
import com.simplertutorials.android.githubtimeline.domain.DetailedUser
import com.simplertutorials.android.githubtimeline.domain.SearchItem
import com.simplertutorials.android.githubtimeline.domain.TimelineItem
import com.simplertutorials.android.githubtimeline.ui.fragments.BasePresenter
import io.reactivex.Observable
import java.util.*

class UserDetailsPresenter(
    private val view: UserDetailsMVP.View,
    private val apiRepository: ApiRepository
) : BasePresenter(view), UserDetailsMVP.Presenter {

    override fun getUserDetails(passedSearchItem: SearchItem) {
        //get User Data from API
        //at the end call API again to get user's repo list
        val disposable =apiRepository
            .getUser(passedSearchItem.name)
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

    override fun getUserTimeline(n: DetailedUser?) {
        //get user repo list from API
        //in the end sort the list by date and notify the view about data change
        view.showLoading()
        view.timelineList.clear()
        val disposable = getUserTimelineObservable(n)
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
    fun getUserTimelineObservable(n:DetailedUser?): Observable<MutableList<TimelineItem>> {
        return apiRepository
            .getUserTimeline(n?.loginName)
    }

    override fun sortList(timelineList: ArrayList<TimelineItem>) {
        //sort the list by repo creating date
        val orderedList = mutableListOf<TimelineItem>()
        orderedList += quickSort(timelineList)
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