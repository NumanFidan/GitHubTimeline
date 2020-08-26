package com.simplertutorials.android.githubtimeline.data.api

import com.simplertutorials.android.githubtimeline.domain.DetailedUser
import com.simplertutorials.android.githubtimeline.domain.TimelineItem
import com.simplertutorials.android.githubtimeline.domain.User
import com.simplertutorials.android.githubtimeline.domain.UserSearchApiResponse
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService{
    @GET ("users/{username}")
    fun getUser(@Path("username")username:String)
            : Single<DetailedUser>

    @GET ("search/users")
    fun getUserList(@Query("q")username:String)
            : Single<UserSearchApiResponse>

    @GET ("users/{username}/repos")
    fun getTimelineItems(@Path("username")username:String)
            : Observable<List<TimelineItem>>
}