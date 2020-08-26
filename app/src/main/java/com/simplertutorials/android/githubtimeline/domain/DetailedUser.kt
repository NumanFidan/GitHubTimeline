package com.simplertutorials.android.githubtimeline.domain

import com.google.gson.annotations.SerializedName

class DetailedUser :
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

    override fun toString(): String {
        if (loginName!= null)
            return loginName as String
        return super.toString()
    }
}