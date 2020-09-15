package com.simplertutorials.android.githubtimeline.domain

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class Repository() : Parcelable {

    @SerializedName("name")
    lateinit var repoName: String

    @SerializedName("description")
    lateinit var description: String

    @SerializedName("language")
    lateinit var language: String

    @SerializedName("stargazers_count")
    var starCount: Int = 0

    @SerializedName("watchers_count")
    var watchersCount: Int = 0

    @SerializedName("forks_count")
    var forks_count: Int = 0

    @SerializedName("open_issues_count")
    var open_issues_count: Int = 0

    constructor(repoName: String, description:String, language:String):this(){
        this.repoName = repoName
        this.description = description
        this.language = language
    }
    constructor(parcel: Parcel) : this() {
        repoName = parcel.readString().toString()
        description = parcel.readString().toString()
        language = parcel.readString().toString()
        starCount = parcel.readInt()
        watchersCount = parcel.readInt()
        forks_count = parcel.readInt()
        open_issues_count = parcel.readInt()
    }


    override fun equals(other: Any?): Boolean {
        if (other is Repository)
            return this.repoName.equals(other.repoName)
                    && this.description.equals(other.description)
                    && this.language.equals(other.language)
                    && this.starCount == other.starCount
                    && this.watchersCount == other.watchersCount
                    && this.forks_count == other.forks_count
                    && this.open_issues_count == other.open_issues_count

        return false
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(repoName)
        parcel.writeString(description)
        parcel.writeString(language)
        parcel.writeInt(starCount)
        parcel.writeInt(watchersCount)
        parcel.writeInt(forks_count)
        parcel.writeInt(open_issues_count)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Repository> {
        override fun createFromParcel(parcel: Parcel): Repository {
            return Repository(parcel)
        }

        override fun newArray(size: Int): Array<Repository?> {
            return arrayOfNulls(size)
        }
    }

}

