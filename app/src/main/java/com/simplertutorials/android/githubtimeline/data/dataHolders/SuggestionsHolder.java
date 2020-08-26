package com.simplertutorials.android.githubtimeline.data.dataHolders;

import com.simplertutorials.android.githubtimeline.domain.User;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class SuggestionsHolder {
    private static SuggestionsHolder instance;
    private ArrayList<User> suggestionsList;
    private SuggestionsHolder (){
        suggestionsList = new ArrayList<User>();
    }

    public static SuggestionsHolder getInstance(){
        if (instance == null){
            instance = new SuggestionsHolder();
        }
        return instance;
    }

    public ArrayList<User> getSuggestionsList() {
        return suggestionsList;
    }
}
