package com.simplertutorials.android.githubtimeline.components;

import com.simplertutorials.android.githubtimeline.ui.MainActivity;
import com.simplertutorials.android.githubtimeline.ui.fragments.searchScreen.SearchScreenFragment;
import com.simplertutorials.android.githubtimeline.ui.fragments.userDetails.UserDetailsFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApiModule.class, ContextModule.class})
public interface ApplicationComponent {

    void inject(UserDetailsFragment userDetailsFragment);
    void inject(SearchScreenFragment searchScreenFragment);
    void inject(MainActivity mainActivity);
}

