package com.simplertutorials.android.githubtimeline.data.api

import com.simplertutorials.android.githubtimeline.domain.DetailedUser
import com.simplertutorials.android.githubtimeline.domain.TimelineItem
import com.simplertutorials.android.githubtimeline.domain.User
import com.simplertutorials.android.githubtimeline.domain.UserSearchApiResponse
import com.simplertutorials.android.githubtimeline.utils.EmptyUserNameException
import com.simplertutorials.android.githubtimeline.utils.TestUtils
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import java.util.concurrent.Callable


class ApiRepositoryTest {

    //Sytem under Test
    lateinit var apiRepository: ApiRepository

    lateinit var apiService: ApiService

    @BeforeEach
    fun init() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { c: Callable<Scheduler?>? -> Schedulers.trampoline() }
        apiRepository = ApiRepository.getInstance()
        apiService = mock(ApiService::class.java)
    }


    /*
        Get user with a username
        verify the correct method is called
        confirm ApiService is triggered
        returns user details item
     */
    @Test
    fun getDetailedUser_getUserWithAUserName_returnsDetailedUser() {

        val expectedUser = DetailedUser(TestUtils.detailedUser)
        val expectedSingle = Single.just(expectedUser)
        `when`(apiService.getUser(anyString())).thenReturn(expectedSingle)

        val returned = ApiRepository.getInstance()
            .getUser(apiService, TestUtils.detailedUserName)
            .test()
            .await()
            .assertValue(expectedUser)
        verify(apiService).getUser(anyString())
        verifyNoMoreInteractions(apiService)
    }

    /*
        Get user with a empty username string
        verify the correct method is called
        confirm ApiService is not triggered
        throws error
     */
    @Test
    fun getDetailedUser_getUserWithAUserName_throwsException() {
        assertThrows(EmptyUserNameException::class.java) {
            val returned = ApiRepository.getInstance()
                .getUser(apiService, "")
                .test()
                .await()
        }
        verifyNoMoreInteractions(apiService)
    }

    /*
        get usersuggestion list
        verify the correct method is called
        confirm ApiService is triggered
        returns usersuggestions
     */
    @Test
    fun searchUser_WithAUserName_returnsUserSuggestions() {

        val expectedResponse = UserSearchApiResponse(listOf<User>(User(TestUtils.user)))
        val expectedSingle = Single.just(expectedResponse)
        `when`(apiService.getUserList(anyString())).thenReturn(expectedSingle)

        val returned = ApiRepository.getInstance()
            .searchUser(apiService, TestUtils.userName)
            .test()
            .await()
            .assertValue(expectedResponse)
        verify(apiService).getUserList(anyString())
        verifyNoMoreInteractions(apiService)
    }

    /*
        get usersuggestion with empty userName
        verify the correct method is called
        confirm ApiService is not triggered
        throws exception
     */

    @Test
    fun searchUser_withEmptyUserName_throwsException() {
        assertThrows(EmptyUserNameException::class.java) {
            val returned = ApiRepository.getInstance()
                .searchUser(apiService, "")
                .test()
                .await()
        }
        verifyNoMoreInteractions(apiService)
    }

    /*
        Get usertimeline with a username
        verify the correct method is called
        confirm ApiService is triggered
        return user repo list
     */
    @Test
    fun getTimeline_WithAUserName_returnsTimelineItems() {

        val timelineItem = TimelineItem(TestUtils.timelineItem_one)
        val expectedResponse = listOf<TimelineItem>(timelineItem)
        val expectedObservable = Single.just(expectedResponse).toObservable()
        `when`(apiService.getTimelineItems(anyString())).thenReturn(expectedObservable)

        val returned = ApiRepository.getInstance()
            .getUserTimeline(apiService, TestUtils.userName)
            .test()
            .await()
            .assertValue(expectedResponse)
        verify(apiService).getTimelineItems(anyString())
        verifyNoMoreInteractions(apiService)
    }

    /*
        Get usertimeline  with empty username
        verify the correct method is called
        confirm ApiService is triggered
        throws exception
     */
    @Test
    fun getTimeline_withEmptyUserName_throwsException() {

        assertThrows(EmptyUserNameException::class.java){
            ApiRepository.getInstance()
                .getUserTimeline(apiService, "")
                .test()
                .await()
        }
        verifyNoMoreInteractions(apiService)
    }

}