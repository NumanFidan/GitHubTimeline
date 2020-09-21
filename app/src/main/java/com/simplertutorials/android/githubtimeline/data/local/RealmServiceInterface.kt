package com.simplertutorials.android.githubtimeline.data.local

import com.simplertutorials.android.githubtimeline.domain.SearchItem
import io.realm.RealmResults

interface RealmServiceInterface {

    fun writeRecentSearchToRealm(searchItem: SearchItem)

    fun getRecentSearchesFromRealm(): RealmResults<SearchItem>?
}