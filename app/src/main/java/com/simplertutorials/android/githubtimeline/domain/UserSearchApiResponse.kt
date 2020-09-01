package com.simplertutorials.android.githubtimeline.domain

import com.google.gson.annotations.SerializedName
import java.lang.StringBuilder

class UserSearchApiResponse() {
    @SerializedName("items")
    lateinit var users: List<User>

    constructor(users:List<User>) : this() {
        this.users = users
    }
    override fun toString(): String {
        var list = StringBuilder(users?.size.toString())
        if (users != null) {
            for (user in users!!){
                list.append(" ,"+user.loginName)
            }
        }
        return list.toString()
    }
}