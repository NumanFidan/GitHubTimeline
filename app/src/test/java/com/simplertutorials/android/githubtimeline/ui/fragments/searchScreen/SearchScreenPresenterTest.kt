package com.simplertutorials.android.githubtimeline.ui.fragments.searchScreen

import com.simplertutorials.android.githubtimeline.data.api.ApiRepository
import com.simplertutorials.android.githubtimeline.data.api.ApiService
import com.simplertutorials.android.githubtimeline.data.local.RealmRepository
import com.simplertutorials.android.githubtimeline.domain.SearchItem
import com.simplertutorials.android.githubtimeline.domain.User
import com.simplertutorials.android.githubtimeline.domain.UserSearchApiResponse
import com.simplertutorials.android.githubtimeline.utils.EmptyUserNameException
import com.simplertutorials.android.githubtimeline.utils.TestUtils
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation
import org.mockito.ArgumentMatchers
import org.mockito.Mockito.*
import java.util.concurrent.Callable

@TestMethodOrder(OrderAnnotation::class)
class SearchScreenPresenterTest {
    //System under Test
    lateinit var presenter: SearchScreenPresenter

    lateinit var view: SearchScreenMVP.View
    lateinit var apiRepository: ApiRepository
    lateinit var realmRepository: RealmRepository

    @BeforeEach
    fun init() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { c: Callable<Scheduler?>? -> Schedulers.trampoline() }
        apiRepository = mock(ApiRepository::class.java)
        realmRepository = mock(RealmRepository::class.java)
        view = mock(SearchScreenMVP.View::class.java)
        presenter =
            SearchScreenPresenter(
                view,
                apiRepository,
                realmRepository
            )
    }

    @AfterEach
    fun validate() {
        validateMockitoUsage()
    }
    /*
        Subscribe to searchbox
        check if emitting changes work
        verify searchBoxObservable only once called
        verify suggestions passing to the behaviourSubject
     */
    @Test
    @Order(1)
    fun subscribeToSearchBox_emittingChangesFromSearchBoxWorks_TriggeredBehaviourObject() {

        //Arrange Api Service
        val expectedResponse = listOf<SearchItem>(SearchItem(TestUtils.user))
        val expectedSingle = Single.just(expectedResponse)
        `when`(apiRepository.searchUser(ArgumentMatchers.anyString())).thenReturn(expectedSingle)
        //Arrange view
        val observable = Observable.just("username")
        `when`(view.searchBoxObservable()).thenReturn(observable)
        val behaviorSubject: BehaviorSubject<List<SearchItem>> = BehaviorSubject.create()
        `when`(view.behaviorSubject).thenReturn(behaviorSubject)

        //Act
        presenter.subscribeToSearchBox()

        //Check
        verify(view, times(1)).searchBoxObservable()
        verify(view, times(1)).behaviorSubject
    }

    /*
        Subscribe to searchbox
        check if emitting error work
        verify showToast function triggered
     */
    @Test
    @Order(2)
    fun subscribeToSearchBox_emittingErrorFromSearchBoxWorks_toastShown() {

        //Arrange view
        val observable =
            Observable.error<String?>(EmptyUserNameException("Username")) as Observable<String>
        `when`(view.searchBoxObservable()).thenReturn(observable)

        //Act
        presenter.subscribeToSearchBox()

        //Check
        verify(view, times(1)).searchBoxObservable()
        verify(view, times(1)).showToast(anyString())

    }

    /*
        Call callApi function with empty text
        verify if currentSuggestions cleared
        verify BehaviourObject triggered
        verify suggestionsRefreshed function triggered
     */
    @Test
    @Order(3)
    fun subscribeToSearchBox_callApiWithEmptyText_suggestionCleared() {

        val behaviorSubject: BehaviorSubject<List<SearchItem>> =
            BehaviorSubject.createDefault(listOf())
        `when`(view.behaviorSubject).thenReturn(behaviorSubject)

        //Act
        presenter.callApi("")

        //Check
        assertEquals(0, view.suggestionsList.size)
        verify(view, times(1)).behaviorSubject
        verify(view, times(1)).suggestionsRefreshed()

    }

    /*
        mock ApiService with successful onNext
        Call callApi funtion with a valid username
        check if ApiService triggered
        verify BehaviourObject triggered
        verify suggesstionRefreshed triggered
     */
    @Test
    @Order(4)
    fun subscribeToSearchBox_callApiWithValidUserName_BehaviourSubjectTriggered() {

        //Arrange Api Service
        val expectedResponse = listOf<SearchItem>(SearchItem(TestUtils.user))
        val expectedSingle = Single.just(expectedResponse)
        `when`(apiRepository.searchUser(anyString())).thenReturn(expectedSingle)

        //Arrange BehaviourObject
        val behaviorSubject: BehaviorSubject<List<SearchItem>> =
            BehaviorSubject.create()
        `when`(view.behaviorSubject).thenReturn(behaviorSubject)

        //Act
        presenter.callApi("username")

        //Check
        verify(apiRepository, times(1)).searchUser(anyString())
        verify(view, times(1)).behaviorSubject
        verify(view, times(1)).suggestionsRefreshed()
        verify(view, times(0)).showTextInputLayoutError(anyString())

    }

    /*
        mock ApiService with empty onNext
        Call callApi funtion with a invalid username
        check if ApiService triggered
        verify BehaviourObject triggered
        verify showTextInputLayoutError triggered
     */
    @Test
    @Order(5)
    fun subscribeToSearchBox_callApiWithInvalidUserName_TextInputLayoutError() {

        //Arrange Api Service
        val invalidUserName = "ghjghjghjghjghj"
        val expectedResponse = listOf<SearchItem>()
        val expectedSingle = Single.just(expectedResponse)
        `when`(apiRepository.searchUser(anyString())).thenReturn(expectedSingle)

        //Arrange BehaviourObject
        val behaviorSubject: BehaviorSubject<List<SearchItem>> =
            BehaviorSubject.create()
        `when`(view.behaviorSubject).thenReturn(behaviorSubject)

        //Act
        presenter.callApi(invalidUserName)

        //Check
        verify(apiRepository, times(1)).searchUser(anyString())
        verify(view, times(1)).behaviorSubject
        verify(view, times(1)).suggestionsRefreshed()
        verify(view, times(1)).showTextInputLayoutError(anyString())

    }

}