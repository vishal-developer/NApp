package com.noon.napp.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.noon.napp.R;
import com.noon.napp.activity.AddSubjectActivity;
import com.noon.napp.adapter.SubjectListAdapter;
import com.noon.napp.model.Subject;
import com.noon.napp.presenter.SubjectPresenter;
import com.noon.napp.view.SubjectView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * Use the {@link SubjectFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SubjectFragment extends Fragment implements SubjectView {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    static final String TAG = SubjectFragment.class.getSimpleName();
    private View view;
    private List<Subject> subjectList;
    private SubjectListAdapter adapter;
    private SubjectPresenter presenter;
    private TextView tvError;

    public SubjectFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SubjectFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SubjectFragment newInstance(String param1, String param2) {
        SubjectFragment fragment = new SubjectFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
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
        view = inflater.inflate(R.layout.fragment_subject, container, false);
        presenter = new SubjectPresenter(getActivity(), this);
        bindItems();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
        presenter.getSubjects();
    }

    private void bindItems() {

        tvError = (TextView) view.findViewById(R.id.tv_error_text);
        view.findViewById(R.id.subject_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddSubjectActivity.class);
                startActivity(intent);
            }
        });

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.subject_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        subjectList = new ArrayList<>();

        adapter = new SubjectListAdapter(getActivity(), subjectList);
        adapter.setOnItemClickLister(new SubjectListAdapter.OnItemClickLister() {
            @Override
            public void onClick(Subject subject, int position) {
                Log.d(TAG, "Subject :" + subject);
                presenter.deleteSubject(subject);
            }
        });
        recyclerView.setAdapter(adapter);

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void getSubjects(List<Subject> subjectList) {
        this.subjectList.clear();
        if (subjectList.size() > 0) {
            this.subjectList.addAll(subjectList);
            tvError.setVisibility(View.GONE);
        } else {
            tvError.setVisibility(View.VISIBLE);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void deleteSubject(int deletedCount) {
        if (deletedCount > 0) {
            presenter.getSubjects();
        }
    }
}
