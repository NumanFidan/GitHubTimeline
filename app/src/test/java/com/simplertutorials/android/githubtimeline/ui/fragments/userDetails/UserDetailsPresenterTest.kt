package com.simplertutorials.android.githubtimeline.ui.fragments.userDetails

import com.simplertutorials.android.githubtimeline.data.api.ApiRepository
import com.simplertutorials.android.githubtimeline.data.api.ApiService
import com.simplertutorials.android.githubtimeline.domain.DetailedUser
import com.simplertutorials.android.githubtimeline.domain.SearchItem
import com.simplertutorials.android.githubtimeline.domain.TimelineItem
import com.simplertutorials.android.githubtimeline.domain.User
import com.simplertutorials.android.githubtimeline.utils.TestUtils
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.mockito.ArgumentCaptor
import org.mockito.ArgumentMatchers
import org.mockito.Mockito.*
import java.util.concurrent.Callable

@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class UserDetailsPresenterTest {
    //System under test
    lateinit var presenter: UserDetailsPresenter

    lateinit var apiRepository: ApiRepository
    lateinit var view: UserDetailsMVP.View

    @BeforeEach
    fun init() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { c: Callable<Scheduler?>? -> Schedulers.trampoline() }

        apiRepository = mock(ApiRepository::class.java)
        view = mock(UserDetailsMVP.View::class.java)
        presenter =
            UserDetailsPresenter(
                view,
                apiRepository
            )
    }

    /*
        get User details with valid user
        verify fillUserDetails with proper username triggered
     */
    @Test
    @Order(1)
    fun getUserDetails_withValidUser_successful() {
        //Arrange ApiService
        val expectedSingle = DetailedUser(TestUtils.detailedUser)
        val expectedReturn = Observable.just(expectedSingle)
        `when`(apiRepository.getUser(anyString())).thenReturn(expectedReturn)

        //Arrange View
        val argument = ArgumentCaptor.forClass(DetailedUser::class.java)

        //Act
        val user = User(TestUtils.user)
        presenter.getUserDetails(SearchItem(user))

        //Check
        verify(view).fillUserDetails(argument.capture())
        assertEquals(user.loginName, argument.value.loginName)
    }

    /*
        try get User details with invalid User details which means Mock ApiService with an error
        verify hideLoading triggered
        verify showToast triggered
     */
    @Test
    @Order(2)
    fun getUserDetails_withInvalidUser_errorHandled() {
        //Arrange ApiService
        val expectedReturn = Observable.error<DetailedUser>(Exception())
        `when`(apiRepository.getUser(anyString())).thenReturn(expectedReturn)

        //Act
        val user = User(TestUtils.user)
        presenter.getUserDetails(SearchItem(user))

        //Check
        verify(view).showToast(ArgumentMatchers.any())
        verify(view, times(1)).hideLoading()
    }

    /*
        get UserTimeline with invalid user
        verify handleServerError triggered
        verify hideLoading triggered
     */
    @Test
    @Order(3)
    fun getUserTimeline_withInvalidUserName_handleServerError(){
        //Arrange ApiService
        val expectedReturn = Observable.error<List<TimelineItem>>(Exception())
        `when`(apiRepository.getUserTimeline(ArgumentMatchers.anyString())).thenReturn(expectedReturn)

        //Arrange View
        val timelineList = ArrayList<TimelineItem>()
        `when`(view.timelineList).thenReturn(timelineList)

        //Act
        val user = DetailedUser(TestUtils.invalidDetailedUser)
        presenter.getUserTimeline(user)

        //check
        verify(view).showLoading()
        verify(view).showToast(ArgumentMatchers.any())
        verify(view).hideLoading()
    }

    /*
        create an unsorted list of timelineItems
        call sortList
        check if list sorted
     */
    @Test
    @Order(4)
    fun sortList_withUnsortedList_successful() {
        //Arrange
        val unsortedList = arrayListOf<TimelineItem>(
            TimelineItem("name1", "Description 1", "2020-06-20T11:00:53Z"),
            TimelineItem("name2", "Description 2", "2020-04-20T11:00:53Z"),
            TimelineItem("name3", "Description 3", "2020-02-20T11:00:53Z"),
            TimelineItem("name4", "Description 4", "2020-10-20T11:00:53Z"),
            TimelineItem("name5", "Description 5", "2019-06-20T11:00:53Z")
        )

        //act
        presenter.sortList(unsortedList)

        //check
        for ( i in 0 until unsortedList.size-1){
            assertTrue(unsortedList[i] >= unsortedList[i+1])
        }
    }
}