package com.simplertutorials.android.githubtimeline.domain

import com.google.gson.annotations.SerializedName

class TimelineItem(
    @SerializedName("name") var repoName: String,
    repoDescription: String,
    created_at: String
) : Comparable<TimelineItem> {

    @SerializedName("description")
    var repoDescription: String = repoDescription

    @SerializedName("url")
    var repoUrl: String? = null

    @SerializedName("created_at")
    var repoCreatedAt: String = created_at

    @SerializedName("updated_at")
    var repoUpdatedAt: String? = null

    @SerializedName("language")
    var repoLanguage: String? = null

    constructor(timelineItem: TimelineItem) : this(
        timelineItem.repoName,
        timelineItem.repoDescription,
        timelineItem.repoCreatedAt
    ) {
        this.repoUrl = timelineItem.repoUrl
        this.repoUpdatedAt = timelineItem.repoUpdatedAt
        this.repoLanguage = timelineItem.repoLanguage
    }

    override fun toString(): String {
        return "*** " + repoName + "," + repoDescription + "," + repoUrl + "," + repoCreatedAt + "," + repoUpdatedAt + "," + repoLanguage + ","
//        return "\n" + repoCreatedAt
    }

    override fun compareTo(other: TimelineItem): Int {
        var thisComparableDate = -1
        var otherComparableDate = -1
        try {
            thisComparableDate = turnStringDateToInt(this.repoCreatedAt)
            otherComparableDate = turnStringDateToInt(other.repoCreatedAt)

            return when {
                thisComparableDate > otherComparableDate -> OBJECT_BIGGER
                thisComparableDate < otherComparableDate -> OBJECT_SMALLER
                else -> OBJECTS_EQUAL
            }
        } catch (e: Exception) {
            if (e is NumberFormatException) {
                return if (thisComparableDate != -1)
                    OBJECT_BIGGER
                else {
                    try {
                        turnStringDateToInt(other.repoCreatedAt)
                        OBJECT_SMALLER
                    } catch (e: Exception) {
                        OBJECTS_EQUAL
                    }
                }
            }
        }
        return OBJECTS_EQUAL
    }

    @Throws(Exception::class)
    private fun turnStringDateToInt(date: String): Int {
        return date.split("T")[0].split('-')
            .joinToString(prefix = "", postfix = "", separator = "").toInt()
    }

    companion object {
        const val OBJECT_BIGGER: Int = 1
        const val OBJECT_SMALLER: Int = -1
        const val OBJECTS_EQUAL: Int = 0
    }
}