package com.simplertutorials.android.githubtimeline.data.local

import com.simplertutorials.android.githubtimeline.domain.RecentSearchItem
import io.realm.RealmResults

interface RealmInterface {

    fun writeRecentSearchToRealm(recentSearchItem: RecentSearchItem)

    fun getRecentSearchesFromRealm(): RealmResults<RecentSearchItem>?
}