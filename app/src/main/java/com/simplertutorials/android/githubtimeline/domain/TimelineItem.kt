package com.simplertutorials.android.githubtimeline.domain

import android.util.Log
import com.google.gson.annotations.SerializedName

class TimelineItem : Comparable<TimelineItem> {

    @SerializedName("name")
    var repoName: String? = null

    @SerializedName("description")
    var repoDescription: String? = null

    @SerializedName("url")
    var repoUrl: String? = null

    @SerializedName("created_at")
    var repoCreatedAt: String? = null

    @SerializedName("updated_at")
    var repoUpdatedAt: String? = null

    @SerializedName("language")
    var repoLanguage: String? = null

    override fun toString(): String {
//        return "+\n*** " + repoName + "\n" + repoDescription + "\n" + repoUrl + "\n" + repoCreatedAt + "\n" + repoUpdatedAt + "\n" + repoLanguage + "\n"
        return "\n"+ repoCreatedAt
    }

    override fun compareTo(other: TimelineItem): Int {
        if (this.repoCreatedAt != null && other.repoCreatedAt!= null) {
            val thisComparableDate = turnStringDateToInt(this.repoCreatedAt!!)
            val otherComparableDate = turnStringDateToInt(other.repoCreatedAt!!)

            return when {
                thisComparableDate > otherComparableDate -> 1
                thisComparableDate < otherComparableDate -> -1
                else -> 0
            }
        }
        return 0
    }

    private fun turnStringDateToInt(date: String): String {
        return date.split("T")[0].split('-')
            .joinToString(prefix = "", postfix = "", separator = "•")
    }
}