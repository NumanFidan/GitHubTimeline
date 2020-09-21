package com.simplertutorials.android.githubtimeline.data.api;

import com.simplertutorials.android.githubtimeline.domain.DetailedUser;
import com.simplertutorials.android.githubtimeline.domain.SearchItem;
import com.simplertutorials.android.githubtimeline.domain.TimelineItem;
import com.simplertutorials.android.githubtimeline.utils.EmptyUserNameException;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@Singleton
public class ApiRepository {
    private ApiService apiService;
    public final String emptyUserNameMessage = "Empty User Name";

    @Inject
    public ApiRepository(ApiService apiService) {
        this.apiService = apiService;
    }
    public Observable<List<TimelineItem>> getUserTimeline(String username) throws Exception{
        if (username.equals(""))
            throw new EmptyUserNameException(emptyUserNameMessage);
        return apiService.getTimelineItems(username)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }

    public Observable<DetailedUser> getUser(String username) throws Exception {
        if (username.equals("")){
            System.out.println("Empty User name");
            throw new EmptyUserNameException(emptyUserNameMessage);
        }
        return apiService.getUser(username)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .toObservable();
    }

    public Single<List<SearchItem>> searchUser (String username) throws Exception {
        if (username.equals(""))
            throw new EmptyUserNameException(emptyUserNameMessage);
        return apiService.getUserList(username)
                .map(item -> item.getUsers())
                .toObservable()
                .flatMapIterable(list -> list)
                .map(item -> new SearchItem(item))
                .toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
