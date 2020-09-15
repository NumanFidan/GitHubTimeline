package com.simplertutorials.android.githubtimeline.data.local

import android.content.Context
import com.simplertutorials.android.githubtimeline.domain.RecentSearchItem
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmResults
import io.realm.Sort
import javax.inject.Inject

open class RealmService () :RealmInterface {
    private var realm: Realm? = null

    constructor(context: Context?, realmConfiguration : RealmConfiguration):this(){
        Realm.init(context)
        realm = Realm.getInstance(realmConfiguration)
    }

    @Inject constructor(context: Context?):this (){
        Realm.init(context)
        realm = Realm.getDefaultInstance()
    }

    override fun writeRecentSearchToRealm(recentSearchItem: RecentSearchItem) {
        realm?.executeTransaction { realm ->
            realm.copyToRealm(recentSearchItem)
        }
    }

    override fun getRecentSearchesFromRealm(): RealmResults<RecentSearchItem>? {
        return realm?.where(RecentSearchItem::class.java)
            ?.sort("date", Sort.DESCENDING)
            ?.findAll()
    }
}
