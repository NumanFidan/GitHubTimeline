package com.simplertutorials.android.githubtimeline.data.local;

import com.simplertutorials.android.githubtimeline.domain.RecentSearchItem;
import com.simplertutorials.android.githubtimeline.domain.User;

import java.util.List;

import io.reactivex.Observable;

public class RealmRepository {
    private static RealmRepository instance;

    private RealmRepository() {

    }

    public static RealmRepository getInstance() {
        if (instance == null)
            instance = new RealmRepository();
        return instance;
    }

    public Observable<List<RecentSearchItem>> getRecentSearches(RealmService realmService) throws Exception {
        // TODO: 9/10/2020
        return null;
    }


    public boolean writeToRecentSearches(RealmService realmService, User user) throws Exception {
        // TODO: 9/10/2020
        return false;
    }
}