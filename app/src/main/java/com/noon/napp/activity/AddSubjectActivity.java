package com.noon.napp.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.noon.napp.R;
import com.noon.napp.databinding.ActivityAddSubjectBinding;
import com.noon.napp.model.Subject;
import com.noon.napp.presenter.AddSubjectPresenter;
import com.noon.napp.utility.FilePathUtil;
import com.noon.napp.view.AddSubjectView;

public class AddSubjectActivity extends AppCompatActivity implements AddSubjectView{

    static final String TAG = AddSubjectActivity.class.getSimpleName();
    public static int GALLERY_REQ_CODE = 1;
    private AddSubjectPresenter presenter;
    private Subject subject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityAddSubjectBinding binding = DataBindingUtil.setContentView(this,
                R.layout.activity_add_subject);



        presenter =  new AddSubjectPresenter(this, this);
        binding.setPresenter(presenter);
        subject = new Subject();
        binding.setSubject(subject);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Add Subject");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    @Override
    public void onSubmitButtonClick(Subject subject) {
        finish();
    }

    @Override
    public void onGalleryBtnClick() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, GALLERY_REQ_CODE);
    }

    @Override
    public void onError(String error) {
        Log.d(TAG, "onError :"+error);
        Snackbar.make((CoordinatorLayout)findViewById(R.id.coordinator_add_sub), error, Snackbar.LENGTH_SHORT)
                .show();
    }

    String path;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_REQ_CODE && resultCode == RESULT_OK && data != null) {
            // Let's read picked image data - its URI
            Uri pickedImage = data.getData();
            // Let's read picked image path using content resolver
            String[] filePath = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(pickedImage, filePath, null, null, null);
            cursor.moveToFirst();
            path = cursor.getString(cursor.getColumnIndex(filePath[0]));

            cursor.close();
        }
        subject.setIconUrl(path);
        Log.d(TAG, path);
    }
}
