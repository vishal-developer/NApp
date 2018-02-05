package com.noon.napp.presenter;

import android.app.Activity;
import android.util.Log;

import com.noon.napp.NApp;
import com.noon.napp.helper.DBHelper;
import com.noon.napp.model.Subject;
import com.noon.napp.view.AddSubjectView;

import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ril on 4/2/18.
 */

public class AddSubjectPresenter {

    static final String TAG = AddSubjectPresenter.class.getSimpleName();
    private Activity activity;
    private AddSubjectView view;
    Subject subject;

    @Inject
    DBHelper dbHelper;

    public AddSubjectPresenter(Activity activity, AddSubjectView view) {
        this.activity = activity;
        this.view = view;
        ((NApp) activity.getApplication()).getAppComponent().inject(this);
    }

    public void onSubmitClick(Subject subject) {
        Log.d(TAG, "onSubmitClick" + subject.toString());
        Log.d(TAG, "userId :" + ((NApp) activity.getApplication()).getUserId());
        if (validateFields(subject)) {

            subject.setUserId(((NApp) activity.getApplication()).getUserId());
            saveSubject(subject).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableObserver<Subject>() {
                        @Override
                        public void onNext(Subject subject) {
                            Log.d(TAG, "subject :" + subject);
                            view.onSubmitButtonClick(subject);
                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                        }

                        @Override
                        public void onComplete() {
                            Log.d(TAG, "onComplete");
                        }
                    });
        }
    }

    private boolean validateFields(Subject subject) {
        StringBuilder errorMsgBuilder = new StringBuilder();
        if (null == subject.getName() || subject.getName().length() < 3) {
            errorMsgBuilder.append("Invalid Name");
        }
        if (null == subject.getDescription() || subject.getDescription().length() < 4) {
            errorMsgBuilder.append(" & Description");
        }

        Log.d(TAG, "errorMsg len: " + errorMsgBuilder.length());
        if (errorMsgBuilder.length() != 0) {
            errorMsgBuilder.append(" !");
            view.onError(errorMsgBuilder.toString());
            return false;
        } else {
            return true;
        }
    }

    public void onGalleryBtnClick() {
        view.onGalleryBtnClick();
    }

    public Observable<Subject> saveSubject(final Subject subject) {
        Log.d(TAG, "saveSubject :");
        return Observable.defer(new Callable<ObservableSource<? extends Subject>>() {
            @Override
            public ObservableSource<? extends Subject> call() throws Exception {
                List<Long> insertCount = dbHelper.subjectDao().insert(subject);
                Log.d(TAG, "insertCount :" + insertCount.toString());
                return Observable.just(subject);
            }
        });
    }
}
