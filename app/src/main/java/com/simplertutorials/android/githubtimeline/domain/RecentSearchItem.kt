package com.simplertutorials.android.githubtimeline.domain

import com.simplertutorials.android.githubtimeline.utils.SearchTypeEnum
import io.realm.RealmObject
import java.util.*

class RecentSearchItem :RealmObject(){
    lateinit var date:Date
    lateinit var name:String
    lateinit var description: String
    lateinit var type : SearchTypeEnum
    lateinit var avatarUrl :String
}