package com.simplertutorials.android.githubtimeline.domain

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

open class User() : Parcelable {

    @SerializedName("login")
    var loginName: String ?=null

    @SerializedName("avatar_url")
    var avatarUrl:String ?= null

    constructor(parcel: Parcel) : this() {
        loginName = parcel.readString()
        avatarUrl = parcel.readString()
    }
    constructor(loginName:String) : this() {
        this.loginName = loginName
    }
    constructor(user:User) : this() {
        this.loginName = user.loginName
        this.avatarUrl = user.avatarUrl
    }


    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(loginName)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }

}

