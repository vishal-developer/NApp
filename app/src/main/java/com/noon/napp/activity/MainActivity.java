package com.noon.napp.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.noon.napp.R;
import com.noon.napp.fragment.DrawFragment;
import com.noon.napp.fragment.HomeFragment;
import com.noon.napp.fragment.SubjectFragment;
import com.noon.napp.helper.DBHelper;
import com.noon.napp.model.User;
import com.noon.napp.presenter.HomePresenter;
import com.noon.napp.view.HomeView;

public class MainActivity extends AppCompatActivity implements HomeView {

    private TextView mTextMessage;
    DBHelper dbHelper;
    HomePresenter homePresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        homePresenter = new HomePresenter(this, this);
        homePresenter.getUser();

        initComponent();
    }

    private void initComponent() {
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_home);
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    setTitle(getString(R.string.app_name));
                    fragment = HomeFragment.newInstance("", "");

                    break;
                case R.id.navigation_dashboard:
                    setTitle(getString(R.string.title_subjects));
                    fragment = SubjectFragment.newInstance("", "");
                    break;
                case R.id.navigation_notifications:
                    setTitle(getString(R.string.title_draw));
                    fragment = DrawFragment.newInstance();
                    break;

            }
            FragmentTransaction fm = getSupportFragmentManager().beginTransaction();
            fm.replace(R.id.frame_layout, fragment).commit();
            return true;
        }
    };

    @Override
    public void getUser(User user) {

    }
}
