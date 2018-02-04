package com.noon.napp.presenter;

import android.app.Activity;
import android.util.Log;

import com.noon.napp.NApp;
import com.noon.napp.helper.DBHelper;
import com.noon.napp.model.Subject;
import com.noon.napp.view.SubjectView;

import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by on 4/2/18.
 */

public class SubjectPresenter {

    static final String TAG = SubjectPresenter.class.getSimpleName();
    private Activity activity;
    private SubjectView subjectView;

    @Inject
    DBHelper db;
    public SubjectPresenter(Activity activity, SubjectView subjectView){
        this.activity = activity;
        this.subjectView = subjectView;
        ((NApp) activity.getApplication()).getAppComponent().inject(this);
    }

    public void getSubjects(){
        querySubjectList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<Subject>>() {
                    @Override
                    public void onNext(List<Subject> subjects) {
                        subjectView.getSubjects(subjects);
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

    public void deleteSubject(Subject subject){
        Log.d(TAG, "deleteSubject :"+subject);
        queryQueryDeleteSubject(subject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Integer>() {
                    @Override
                    public void onNext(Integer deletedCount) {
                        Log.d(TAG, "deletedCount :"+deletedCount);
                        subjectView.deleteSubject(deletedCount);
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



    private Observable<Integer> queryQueryDeleteSubject(final Subject subject){
        return Observable.defer(new Callable<ObservableSource<? extends Integer>>() {
            @Override
            public ObservableSource<? extends Integer> call() throws Exception {
                int deletedCount = db.subjectDao().delete(subject);
                return Observable.just(deletedCount);
            }
        });
    }

    private Observable<List<Subject>> querySubjectList(){
        return Observable.defer(new Callable<ObservableSource<? extends List<Subject>>>() {
            @Override
            public ObservableSource<? extends List<Subject>> call() throws Exception {
                List<Subject> subjectList = db.subjectDao().getAll();
                return Observable.just(subjectList);
            }
        });
    }
}
