package com.simplertutorials.android.githubtimeline.data.dataHolders;

import com.simplertutorials.android.githubtimeline.domain.User;
import java.util.ArrayList;

public class DataRepository {

    private static DataRepository instance;

    private DataRepository (){

    }

    public static DataRepository getInstance(){
        if (instance == null){
            instance = new DataRepository();
        }
        return instance;
    }

    public ArrayList<User> getCurrentSuggestions(){
        return SuggestionsHolder.getInstance().getSuggestionsList();
    }
}
