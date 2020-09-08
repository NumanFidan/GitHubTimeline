package com.simplertutorials.android.githubtimeline.utils

import com.simplertutorials.android.githubtimeline.domain.DetailedUser
import com.simplertutorials.android.githubtimeline.domain.TimelineItem
import com.simplertutorials.android.githubtimeline.domain.User

class TestUtils {
    companion object {
        const val timeStamp_smaller = "2020-06-20T11:00:53Z"

        @JvmStatic
        val timelineItem_one = TimelineItem(
            "GitHubTimeline", "Shows users GitHub Repos",
            timeStamp_smaller
        )

        const val timeStamp_bigger = "2020-08-26T07:56:48Z"

        @JvmStatic
        val timelineItem_two = TimelineItem(
            "MileStone", "Shows users Popular Movies",
            timeStamp_bigger
        )

        const val detailedUserName = "foo"

        @JvmStatic
        val detailedUser = DetailedUser(
            detailedUserName,
            "Berlin",
            "https://www.github.com",
            12,
            12,
            detailedUserName)

        const val userName = "foo"

        @JvmStatic
        val user= User(detailedUserName)

        const val invalidUserName = "ghjghjghjghjghj"

        @JvmStatic
        val invalidDetailedUser = DetailedUser(
            invalidUserName,
            "Berlin",
            "https://www.github.com",
            12,
            12,
            invalidUserName)
    }
}