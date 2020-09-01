package com.simplertutorials.android.githubtimeline.data.api;

import com.simplertutorials.android.githubtimeline.domain.DetailedUser;
import com.simplertutorials.android.githubtimeline.domain.TimelineItem;
import com.simplertutorials.android.githubtimeline.domain.UserSearchApiResponse;
import com.simplertutorials.android.githubtimeline.utils.EmptyUserNameException;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ApiRepository {
    private static ApiRepository instance;
    public final String emptyUserNameMessage = "Empty User Name";

    private ApiRepository() {

    }

    public static ApiRepository getInstance() {
        if (instance == null)
            instance = new ApiRepository();
        return instance;
    }

    public Observable<List<TimelineItem>> getUserTimeline(ApiService apiService, String username) throws Exception{
        if (username.equals(""))
            throw new EmptyUserNameException(emptyUserNameMessage);
        return apiService.getTimelineItems(username)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }


    public Observable<DetailedUser> getUser(ApiService apiService, String username) throws Exception {
        if (username.equals("")){
            System.out.println("Empty User name");
            throw new EmptyUserNameException(emptyUserNameMessage);
        }
        return apiService.getUser(username)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .toObservable();

    }

    public Observable<UserSearchApiResponse> searchUser (ApiService apiService, String username) throws Exception {
        if (username.equals(""))
            throw new EmptyUserNameException(emptyUserNameMessage);
        return apiService.getUserList(username)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .toObservable();
    }
}
