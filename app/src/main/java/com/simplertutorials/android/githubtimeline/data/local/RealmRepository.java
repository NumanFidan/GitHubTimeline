package com.simplertutorials.android.githubtimeline.data.local;

import android.os.Build;
import androidx.annotation.RequiresApi;
import com.simplertutorials.android.githubtimeline.domain.RecentSearchItem;
import com.simplertutorials.android.githubtimeline.domain.Repository;
import com.simplertutorials.android.githubtimeline.domain.User;
import java.util.Date;
import javax.inject.Inject;
import javax.inject.Singleton;
import io.realm.RealmResults;
import okhttp3.internal.http.RealInterceptorChain;

@Singleton
public class RealmRepository {

    private RealmInterface realmService;

    @Inject
    public RealmRepository(RealmService realmService) {
        this.realmService = realmService;
    }

    public RealmRepository(RealmInterface realmInterface) {
        this.realmService = realmInterface;
    }

    public RealmResults<RecentSearchItem> getRecentSearches() throws Exception {
        return realmService.getRecentSearchesFromRealm();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean writeToRecentSearches(Object object) throws Exception {
        realmService.writeRecentSearchToRealm(objectToRecentSearchItem(object));
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private RecentSearchItem objectToRecentSearchItem(Object object) throws Exception {
        RecentSearchItem recentSearchItem;

        if (object instanceof User)
            recentSearchItem = new RecentSearchItem((User)object);
        else if (object instanceof Repository)
            recentSearchItem = new RecentSearchItem((Repository) object);
        else
            throw new Exception();

        Date date = new Date(System.currentTimeMillis());
        recentSearchItem.date = date;
        return recentSearchItem;
    }
}