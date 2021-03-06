package com.simplertutorials.android.githubtimeline.ui.fragments.searchScreen

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.simplertutorials.android.githubtimeline.MainApplication
import com.simplertutorials.android.githubtimeline.R
import com.simplertutorials.android.githubtimeline.data.api.ApiRepository
import com.simplertutorials.android.githubtimeline.data.local.RealmRepository
import com.simplertutorials.android.githubtimeline.domain.SearchItem
import com.simplertutorials.android.githubtimeline.ui.MainActivity
import com.simplertutorials.android.githubtimeline.ui.adapters.SuggestionsAdapter
import com.simplertutorials.android.githubtimeline.ui.customListeners.UserSuggestionOnClick
import com.simplertutorials.android.githubtimeline.ui.fragments.BaseFragment
import com.simplertutorials.android.githubtimeline.ui.fragments.userDetails.UserDetailsFragment
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import kotlinx.android.synthetic.main.search_screen_fragment.*
import kotlinx.android.synthetic.main.search_screen_fragment.view.*
import javax.inject.Inject


open class SearchScreenFragment : BaseFragment(), UserSuggestionOnClick,
    SearchScreenMVP.View {
    private val ARG_USER_PARAM: String = "current_user"
    private lateinit var _presenter: SearchScreenPresenter
    private lateinit var activity: MainActivity
    private lateinit var recyclerViewAdapter: SuggestionsAdapter
    private lateinit var mSuggestionsList: ArrayList<SearchItem>
    private lateinit var searchBox: EditText
    private lateinit var suggestionsBehaviourSubject: BehaviorSubject<List<SearchItem>>

    @Inject
    lateinit var apiRepository: ApiRepository
    @Inject
    lateinit var realmRepository: RealmRepository

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity.applicationContext as MainApplication).component.inject(this)
        _presenter =
            SearchScreenPresenter(
                this,
                apiRepository,
                realmRepository
            )
        mSuggestionsList = ArrayList<SearchItem>()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.search_screen_fragment, container, false)
        updateUi(view)
        subscribeSuggestionsBehaviourSubject()
        return view
    }

    @SuppressLint("CheckResult")
    private fun subscribeSuggestionsBehaviourSubject() {
        suggestionsBehaviourSubject = BehaviorSubject.create()
        suggestionsBehaviourSubject.subscribe(){ n->
                suggestionsList.clear()
                suggestionsList.addAll(n)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.activity = context as MainActivity
    }

    override fun onDetach() {
        super.onDetach()
        _presenter.onDetach()
    }

    private fun updateUi(view: View) {
        //update or initialize all UI elements
        searchBox = view.searchBox
        _presenter.subscribeToSearchBox()

        setUpRecyclerView(view.searchSuggestions)
    }

    private fun setUpRecyclerView(searchSuggestions: RecyclerView?) {
        recyclerViewAdapter = SuggestionsAdapter(
            suggestionsList,
            this
        )

        val layoutManager = LinearLayoutManager(context)
        searchSuggestions?.apply {
            setHasFixedSize(true)
            adapter = recyclerViewAdapter
            this.layoutManager = layoutManager
        }
    }

    override val behaviorSubject: BehaviorSubject<List<SearchItem>>
        get() = this.suggestionsBehaviourSubject
    override val suggestionsList: ArrayList<SearchItem>
        get() = this.mSuggestionsList

    override fun searchBoxObservable(): Observable<String>? {
        //create observable to observe typing in Search Box
        return Observable
            .create<String> { emitter ->
                // Listen for text input into the SearchView
                searchBox.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                    }

                    override fun beforeTextChanged(
                        s: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int
                    ) {
                    }

                    override fun onTextChanged(
                        s: CharSequence?,
                        start: Int,
                        before: Int,
                        count: Int
                    ) {
                        if (!emitter.isDisposed) {
                            //show progressbar to user and emit the text changes
                            setProgressBarVisibility(true)
                            emitter.onNext(s.toString())
                        }
                    }
                })
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun showTextInputLayoutError(message:String){
        searchBox.error = message
    }

    fun setProgressBarVisibility(userTyping: Boolean) {
        //show progressbar to  the user or hide it and show suggestions
        if (userTyping) {
            searchingProgressBar.visibility = View.VISIBLE
            searchSuggestions.visibility = View.INVISIBLE
        } else {
            searchSuggestions.visibility = View.VISIBLE
            searchingProgressBar.visibility = View.GONE
        }
    }

    override fun suggestionsRefreshed() {
        //hide progressbar and notify the suggestion adapter about new suggestions
        setProgressBarVisibility(false)
        recyclerViewAdapter.notifyDataSetChanged()
    }

    override fun onUserSuggestionClicked(searchItem: SearchItem) {
        //write Search to Realm
        _presenter.writeUserToRealm(searchItem);
        //pass user to the UserDetails screen
        val fragment =
            UserDetailsFragment()
        val args = Bundle()
        args.putParcelable(ARG_USER_PARAM, searchItem)
        fragment.arguments = args
        activity.changeFragment(R.id.container, fragment)
    }

}