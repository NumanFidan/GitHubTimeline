package com.simplertutorials.android.githubtimeline.data.local

import android.content.Context
import com.simplertutorials.android.githubtimeline.domain.RecentSearchItem
import io.realm.Realm
import io.realm.RealmResults
import io.realm.Sort
import javax.inject.Inject

class RealmService {
    private var realm: Realm? = null

    @Inject
    fun RealmService(context: Context?) {
        Realm.init(context)
        realm = Realm.getDefaultInstance()
    }

    fun writeRecentSearchToRealm(recentSearchItem: RecentSearchItem) {
        realm?.executeTransaction { realm ->
            realm.copyToRealm(recentSearchItem)
        }
    }

    fun getRecentSearchsFromReaml(): RealmResults<RecentSearchItem>? {
        return realm?.where(RecentSearchItem::class.java)
            ?.sort("date", Sort.DESCENDING)
            ?.findAll()
    }
}
