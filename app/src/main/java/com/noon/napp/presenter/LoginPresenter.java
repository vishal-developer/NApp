package com.noon.napp.presenter;

import android.app.Activity;
import android.support.design.widget.Snackbar;
import android.util.Log;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.noon.napp.NApp;
import com.noon.napp.R;
import com.noon.napp.helper.DBHelper;
import com.noon.napp.model.User;
import com.noon.napp.view.LoginView;

import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ril on 5/2/18.
 */

public class LoginPresenter {

    static final String TAG = LoginPresenter.class.getSimpleName();
    private Activity activity;
    private LoginView view;

    @Inject
    DBHelper dbHelper;

    public LoginPresenter(Activity activity, LoginView view){
        this.activity = activity;
        this.view = view;
        ((NApp)activity.getApplication()).getAppComponent().inject(this);
    }

    public void manageLogin(GoogleSignInAccount account){
        Log.d(TAG, "account"+account);
        if(null != account){
            Log.d(TAG, "account"+account);
            User user = new User(account.getId(), account.getDisplayName(),
                    account.getEmail());
            saveUser(user).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableObserver<User>() {
                        @Override
                        public void onNext(User user) {
                            Log.d(TAG, "user"+user);
                            if(null != user){
                                ((NApp)activity.getApplication()).setUserId(user.getFbId());
                                view.loginSuccess();
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                            view.onLoginError("Not able to insert user data!");
                        }

                        @Override
                        public void onComplete() {
                            Log.d(TAG, "onComplete");

                        }
                    });
        }

    }

    private Observable<User> saveUser(final User user) {
        return Observable.defer(new Callable<ObservableSource<? extends User>>() {
            @Override
            public ObservableSource<? extends User> call() throws Exception {

                Log.d(TAG,"saveUser");
//                DBHelper dbHelper = ((NApp)getApplication()).getDb();
                Log.d(TAG,"dbHelper :"+dbHelper);
                List<Long> insertList = dbHelper.userDao().insert(user);
                Log.d(TAG,"saveUser after insertions"+insertList.size());
                return Observable.just(user);
            }
        });
    }
}
