package com.simplertutorials.android.githubtimeline.data.local;

import com.simplertutorials.android.githubtimeline.domain.SearchItem;
import com.simplertutorials.android.githubtimeline.domain.Repository;
import com.simplertutorials.android.githubtimeline.domain.User;
import java.util.Date;
import javax.inject.Inject;
import javax.inject.Singleton;
import io.realm.RealmResults;

@Singleton
public class RealmRepository {

    private RealmServiceInterface realmService;

    @Inject
    public RealmRepository(RealmService realmService) {
        this.realmService = realmService;
    }

    public RealmRepository(RealmServiceInterface realmServiceInterface) {
        this.realmService = realmServiceInterface;
    }

    public RealmResults<SearchItem> getRecentSearches() throws Exception {
        return realmService.getRecentSearchesFromRealm();
    }

    public boolean writeToRecentSearches(Object object) throws Exception {
        realmService.writeRecentSearchToRealm(objectToRecentSearchItem(object));
        return false;
    }

    private SearchItem objectToRecentSearchItem(Object object) throws Exception {
        SearchItem searchItem;
        if (object instanceof SearchItem)
            searchItem = (SearchItem) object;
        else if (object instanceof User)
            searchItem = new SearchItem((User) object);
        else if (object instanceof Repository)
            searchItem = new SearchItem((Repository) object);
        else
            throw new Exception();

        Date date = new Date(System.currentTimeMillis());
        searchItem.date = date;
        return searchItem;
    }
}