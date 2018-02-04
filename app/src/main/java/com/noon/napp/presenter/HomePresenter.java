package com.noon.napp.presenter;

import android.app.Activity;
import android.util.Log;

import com.noon.napp.NApp;
import com.noon.napp.helper.DBHelper;
import com.noon.napp.model.User;
import com.noon.napp.view.HomeView;

import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by on 3/2/18.
 */

public class HomePresenter {

    static final String TAG = HomePresenter.class.getSimpleName();
    private Activity activity;
    private HomeView view;
    @Inject
    DBHelper dbHelper;


    public HomePresenter(Activity activity, HomeView view) {
        this.activity = activity;
        this.view = view;
        ((NApp) activity.getApplication()).getAppComponent().inject(this);
    }

    public void getUser() {
        getList().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<User>>() {
                    @Override
                    public void onNext(List<User> list) {
                        if (null != list && !list.isEmpty()) {
                            view.getUser(list.get(0));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }

    private Observable<List<User>> getList() {
        return Observable.defer(new Callable<ObservableSource<? extends List<User>>>() {
            @Override
            public ObservableSource<? extends List<User>> call() throws Exception {
                List<User> users = dbHelper.userDao().getAll();
                for (User user : users
                        ) {
                    Log.d(TAG, user.toString());
                }
                return Observable.just(users);
            }
        });
    }
}
