package com.simplertutorials.android.githubtimeline.domain

import android.os.Parcel
import android.os.Parcelable
import com.simplertutorials.android.githubtimeline.utils.SearchTypeEnum
import io.realm.RealmObject
import java.util.*

open class SearchItem() : RealmObject() , Parcelable {
    lateinit var date: Date
    lateinit var name: String
    lateinit var description: String
    lateinit var avatarUrl: String
    var repoLanguage: String = ""
    var repoStarCount: Int = 0
    var repoWatchersCount: Int = 0
    var repoForksCount: Int = 0
    var repoOpenIssuesCount: Int = 0
    private var typeString: String =""
    var type: SearchTypeEnum
        get() {
            return SearchTypeEnum.valueOf(typeString)
        }
        set(value) {
            typeString = value.name
        }

    constructor(parcel: Parcel) : this() {
        name = parcel.readString().toString()
        description = parcel.readString().toString()
        avatarUrl = parcel.readString().toString()
        repoLanguage = parcel.readString().toString()
        repoStarCount = parcel.readInt()
        repoWatchersCount = parcel.readInt()
        repoForksCount = parcel.readInt()
        repoOpenIssuesCount = parcel.readInt()
        typeString = parcel.readString().toString()
    }

    constructor(user: User):this(){
        this.name = user.loginName
        this.description = user.userId.toString()
        if (user.avatarUrl != null )
            this.avatarUrl = user.avatarUrl!!
        this.type = SearchTypeEnum.USER
    }

    constructor(repository: Repository):this(){
        this.name = repository.repoName
        this.description = repository.description
        this.type = SearchTypeEnum.REPOSITORY
        this.repoLanguage = repository.language
        this.repoStarCount = repository.starCount
        this.repoForksCount = repository.forks_count
        this.repoOpenIssuesCount = repository.open_issues_count
        this.repoWatchersCount = repository.watchersCount
    }

    override fun equals(other: Any?): Boolean {
        if (other is SearchItem){
            if (name.equals(other.name)&& type == other.type)
                return true
        }
        return false
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(description)
        parcel.writeString(avatarUrl)
        parcel.writeString(repoLanguage)
        parcel.writeInt(repoStarCount)
        parcel.writeInt(repoWatchersCount)
        parcel.writeInt(repoForksCount)
        parcel.writeInt(repoOpenIssuesCount)
        parcel.writeString(typeString)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SearchItem> {
        override fun createFromParcel(parcel: Parcel): SearchItem {
            return SearchItem(parcel)
        }

        override fun newArray(size: Int): Array<SearchItem?> {
            return arrayOfNulls(size)
        }
    }
}