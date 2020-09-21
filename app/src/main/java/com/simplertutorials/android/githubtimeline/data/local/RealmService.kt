package com.simplertutorials.android.githubtimeline.data.local

import android.content.Context
import com.simplertutorials.android.githubtimeline.domain.SearchItem
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmResults
import io.realm.Sort
import javax.inject.Inject

open class RealmService () :RealmServiceInterface {
    private var realm: Realm? = null

    constructor(context: Context?, realmConfiguration : RealmConfiguration):this(){
        Realm.init(context)
        realm = Realm.getInstance(realmConfiguration)
    }

    @Inject constructor(context: Context?):this (){
        Realm.init(context)
        realm = Realm.getDefaultInstance()
    }

    override fun writeRecentSearchToRealm(searchItem: SearchItem) {
        realm?.executeTransaction { realm ->
            realm.copyToRealm(searchItem)
        }
    }

    override fun getRecentSearchesFromRealm(): RealmResults<SearchItem>? {
        return realm?.where(SearchItem::class.java)
            ?.sort("date", Sort.DESCENDING)
            ?.findAll()
    }
}
