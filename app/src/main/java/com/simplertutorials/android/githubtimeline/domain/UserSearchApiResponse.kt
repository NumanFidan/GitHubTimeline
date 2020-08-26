package com.simplertutorials.android.githubtimeline.domain

import com.google.gson.annotations.SerializedName
import java.lang.StringBuilder

class UserSearchApiResponse {
    @SerializedName("items")
    val users: List<User>? = null

    override fun toString(): String {
        var list :StringBuilder = StringBuilder(users?.size.toString())
        if (users != null) {
            for (user in users){
                list.append(" ,"+user.loginName)
            }
        }
        return list.toString()
    }
}