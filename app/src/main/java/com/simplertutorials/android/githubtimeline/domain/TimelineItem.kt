package com.simplertutorials.android.githubtimeline.domain

import android.util.Log
import com.google.gson.annotations.SerializedName
import java.io.FileDescriptor
import java.lang.Exception
import java.lang.NumberFormatException

class TimelineItem(
    @SerializedName("name") var repoName: String,
    repoDescripton: String,
    created_at: String
) : Comparable<TimelineItem> {

    @SerializedName("description")
    var repoDescription: String = repoDescripton

    @SerializedName("url")
    var repoUrl: String? = null

    @SerializedName("created_at")
    var repoCreatedAt: String = created_at

    @SerializedName("updated_at")
    var repoUpdatedAt: String? = null

    @SerializedName("language")
    var repoLanguage: String? = null

    override fun toString(): String {
//        return "+\n*** " + repoName + "\n" + repoDescription + "\n" + repoUrl + "\n" + repoCreatedAt + "\n" + repoUpdatedAt + "\n" + repoLanguage + "\n"
        return "\n" + repoCreatedAt
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