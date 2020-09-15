package com.simplertutorials.android.githubtimeline.data.local

import com.nhaarman.mockitokotlin2.whenever
import com.simplertutorials.android.githubtimeline.domain.RecentSearchItem
import com.simplertutorials.android.githubtimeline.domain.Repository
import com.simplertutorials.android.githubtimeline.domain.User
import com.simplertutorials.android.githubtimeline.utils.TestUtils
import io.reactivex.Flowable
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmObject
import io.realm.RealmResults
import org.junit.jupiter.api.*
import org.mockito.Mockito
import org.mockito.Mockito.*


@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class RealmRepositoryTest {
    //System under test
    lateinit var realmRepository: RealmRepository

    lateinit var realmInterface: RealmInterface

    @BeforeEach
    fun init() {
        realmInterface = mock(RealmInterface::class.java)
        realmRepository = RealmRepository(realmInterface)
    }

    @AfterEach
    fun validate() {
        validateMockitoUsage()
    }

    private fun <T> anyObject(): T {
        Mockito.anyObject<T>()
        return uninitialized()
    }

    private fun <T> uninitialized(): T = null as T

    /*
        try to pass an User object to writeToRecentSearches
        verify called "writeRecentSearchToRealm" from RealmService
     */
    @Test
    @Order(1)
    fun writeToRecentSearches_passAnUser_realmServiceCalled() {
        //arrange
        val user = User(TestUtils.user)
        doNothing().whenever(realmInterface).writeRecentSearchToRealm(anyObject())

        //Act
        realmRepository.writeToRecentSearches(user)

        //Check
        verify(realmInterface).writeRecentSearchToRealm(anyObject())
    }

    /*
        try to pass a Repository object to writeToRecentSearches
        verify called "writeRecentSearchToRealm" from RealmService
     */
    @Test
    @Order(2)
    fun writeToRecentSearches_passAnRepository_realmServiceCalled() {
        //arrange
        val repository = Repository("userName", "description", "Kotlin")
        doNothing().whenever(realmInterface).writeRecentSearchToRealm(anyObject())

        //Act
        realmRepository.writeToRecentSearches(repository)

        //Check
        verify(realmInterface).writeRecentSearchToRealm(anyObject())
    }

    /*
        try to pass an object which is not an instance of User or Repository to writeToRecentSearches
        verify Exception threw
        verify "writeRecentSearchToRealm" is not called from RealmService
     */
    @Test
    @Order(3)
    fun writeToRecentSearches_passAnInvalidObject_throwException() {
        //arrange
        val inValidObject = "inValidObject"
        doNothing().whenever(realmInterface).writeRecentSearchToRealm(anyObject())

        //Act

        //Act and Check
        Assertions.assertThrows(Exception::class.java) {
            realmRepository.writeToRecentSearches(inValidObject)
        }
        verifyNoMoreInteractions(realmInterface)
    }
}
