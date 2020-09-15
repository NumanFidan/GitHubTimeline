package com.simplertutorials.android.githubtimeline.domain

import com.simplertutorials.android.githubtimeline.utils.SearchTypeEnum
import io.realm.RealmObject
import java.util.*

open class RecentSearchItem() : RealmObject() {
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
        if (other is RecentSearchItem){
            if (name.equals(other.name)&& type == other.type)
                return true
        }
        return false
    }
}