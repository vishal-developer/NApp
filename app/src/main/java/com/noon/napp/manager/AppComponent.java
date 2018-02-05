package com.noon.napp.manager;

import com.noon.napp.presenter.AddSubjectPresenter;
import com.noon.napp.presenter.HomePresenter;
import com.noon.napp.presenter.LoginPresenter;
import com.noon.napp.presenter.SubjectPresenter;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Vishal on 4/2/18.
 */

@Singleton
@Component(dependencies = {}, modules = {AppModule.class, DBManager.class})
public interface AppComponent {
    void inject(LoginPresenter loginPresenter);

    void inject(AddSubjectPresenter addSubjectPresenter);

    void inject(HomePresenter homePresenter);

    void inject(SubjectPresenter subjectPresenter);
}
