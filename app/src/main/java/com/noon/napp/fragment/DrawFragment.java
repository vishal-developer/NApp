package com.noon.napp.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.noon.napp.R;
import com.noon.napp.utility.FilePathUtil;
import com.rm.freedrawview.FreeDrawView;
import com.rm.freedrawview.ResizeBehaviour;

import java.io.File;
import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * Use the {@link DrawFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DrawFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    static final String TAG = DrawFragment.class.getSimpleName();
    private View view;
    private FreeDrawView mSignatureView;

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {Manifest.permission.WRITE_EXTERNAL_STORAGE};

    public DrawFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment
     *
     * @return A new instance of fragment DrawFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DrawFragment newInstance() {
        DrawFragment fragment = new DrawFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_draw, container, false);
        verifyStoragePermissions(getActivity());
        initComponent();
        return view;
    }

    private void initComponent() {
        mSignatureView = (FreeDrawView) view.findViewById(R.id.draw_panel);

        // Setup the View
        mSignatureView.setPaintColor(Color.BLACK);
        //mSignatureView.setPaintWidthDp(6);
        mSignatureView.setPaintAlpha(255);// from 0 to 255
        mSignatureView.setResizeBehaviour(ResizeBehaviour.CROP);


        Button btnUndo, btnRedo, btnColors, btnSave;
        btnUndo = (Button) view.findViewById(R.id.btn_undo);
        btnUndo.setOnClickListener(clickListener);

        btnRedo = (Button) view.findViewById(R.id.btn_redo);
        btnRedo.setOnClickListener(clickListener);

        btnColors = (Button) view.findViewById(R.id.btn_color);
        btnColors.setOnClickListener(clickListener);

        btnSave = (Button) view.findViewById(R.id.btn_save);
        btnSave.setOnClickListener(clickListener);

    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_undo:
                    mSignatureView.undoLast();
                    break;
                case R.id.btn_redo:
                    mSignatureView.redoLast();
                    break;
                case R.id.btn_color:
                    mSignatureView.setPaintColor(getResources().getColor(R.color.colorAccent));
                    break;
                case R.id.btn_save:
                    saveDrawing();
                    break;
            }

        }
    };

    private void saveDrawing() {

        mSignatureView.getDrawScreenshot(new FreeDrawView.DrawCreatorListener() {
            @Override
            public void onDrawCreated(Bitmap draw) {
                if (addJpgSignatureToGallery(draw,
                        FilePathUtil.getStorageDir(getString(R.string.app_name)))) {
                    Toast.makeText(getActivity(), "Signature saved into the Gallery", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Unable to store the signature", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onDrawCreationError() {

            }
        });

    }

    public boolean addJpgSignatureToGallery(Bitmap signature, File dirPath) {
        boolean result = false;
        try {
            File photo = new File(dirPath, String.format("image_%d.jpg", System.currentTimeMillis()));
            FilePathUtil.saveBitmapToJPG(signature, photo);
            scanMediaFile(photo);
            result = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


    private void scanMediaFile(File photo) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri contentUri = Uri.fromFile(photo);
        mediaScanIntent.setData(contentUri);
        getActivity().sendBroadcast(mediaScanIntent);
    }


    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length <= 0
                        || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getActivity(), "Cannot write images to external storage", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    // TODO: Rename method, update argument and hook method into UI event

}
