package com.simplertutorials.android.githubtimeline;

import android.app.Application;

import com.simplertutorials.android.githubtimeline.components.ApplicationComponent;
import com.simplertutorials.android.githubtimeline.components.ContextModule;
import com.simplertutorials.android.githubtimeline.components.DaggerApplicationComponent;

public class MainApplication extends Application {

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        applicationComponent = DaggerApplicationComponent.builder()
                .contextModule(new ContextModule(this))
                .build();
    }
    public ApplicationComponent getComponent(){
        return applicationComponent;
    }
}
