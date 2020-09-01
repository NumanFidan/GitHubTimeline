package com.simplertutorials.android.githubtimeline.domain

import com.google.gson.annotations.SerializedName

open class DetailedUser ():
    User() {

    @SerializedName("name")
    var userName:String ?= null
    @SerializedName("location")
    var location:String ?= null
    @SerializedName("blog")
    var siteUrl:String ?= null
    @SerializedName("followers")
    var followerCount:Int?= null
    @SerializedName("following")
    var followingCount:Int?= null

    constructor(userName:String, location:String, siteUrl:String, followerCount:Int, followingCount:Int) : this() {
        this.userName = userName
        this.location = location
        this.siteUrl = siteUrl
        this.followerCount = followerCount
        this.followingCount = followingCount
    }
    constructor(detailedUser: DetailedUser) : this() {
        this.userName = detailedUser.userName
        this.location = detailedUser.location
        this.siteUrl = detailedUser.siteUrl
        this.followerCount = detailedUser.followerCount
        this.followingCount = detailedUser.followingCount
    }
    override fun toString(): String {
        if (loginName!= null)
            return loginName as String
        return super.toString()
    }

    override fun equals(other: Any?): Boolean {
        if (other is DetailedUser){
            if (other.userName == this.userName)
                return true
        }
        return false
    }
}