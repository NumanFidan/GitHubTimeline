package com.simplertutorials.android.githubtimeline.data.local

import androidx.test.core.app.ApplicationProvider
import com.simplertutorials.android.githubtimeline.domain.RecentSearchItem
import com.simplertutorials.android.githubtimeline.utils.TestUtils
import io.realm.Realm
import io.realm.RealmConfiguration
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class RealmServiceTest1 {

    //System under test
    lateinit var realmService: RealmService

    @Before
    fun init() {
        Realm.init(ApplicationProvider.getApplicationContext())
        val testConfig = RealmConfiguration.Builder()
            .inMemory()
            .name("test-Realm")
            .build()
        realmService = RealmService(ApplicationProvider.getApplicationContext(), testConfig)
    }

    /*
        write RecentSearchItem to Realm
        verify RecentSearchItem inserted
        Read RecentSearchItem to Realm
        verify sorted correctly
     */
    @Test
    fun writeRecentSearch_writeRead_success() {
        // Arrange Objects
        val recentSearchItem = RecentSearchItem(TestUtils.user)
        recentSearchItem.avatarUrl = ""
        val date = Date(System.currentTimeMillis())
        recentSearchItem.date = date
        val recentSearchItem2 = RecentSearchItem(TestUtils.user)
        recentSearchItem2.avatarUrl = ""
        val date2 = Date(
            LocalDateTime.of(2016, 3, 4, 15, 3, 15)
                .format(DateTimeFormatter.ofPattern("M/d/y H:m:ss"))
        )
        recentSearchItem2.date = date2

        //Act- Write To Realm
        realmService.writeRecentSearchToRealm(recentSearchItem)
        realmService.writeRecentSearchToRealm(recentSearchItem2)
        //Act- Read from Realm
        val results = realmService.getRecentSearchesFromRealm()

        //Verify Results - values written in RealmDatabase
        assertEquals(recentSearchItem, results?.get(0))
        assertEquals(recentSearchItem2, results?.get(1))

        //Verify Results - values is sorted as expected
        if (results != null) {
            for (i in 0 until results.size - 1) {
                val comparision = results.get(i)?.date?.compareTo(results.get(i + 1)?.date)
                assertEquals(1, comparision)
            }
        }
    }
}