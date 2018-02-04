package com.noon.napp.adapter;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.noon.napp.R;
import com.noon.napp.databinding.LayoutSubjectItemBinding;
import com.noon.napp.model.Subject;
import com.noon.napp.utility.FilePathUtil;

import java.util.List;

/**
 * Created by ril on 4/2/18.
 */

public class SubjectListAdapter extends RecyclerView.Adapter<SubjectListAdapter.ViewHolder> {
    static final String TAG = SubjectListAdapter.class.getSimpleName();
    private Activity activity;
    private List<Subject> subjectList;
    private OnItemClickLister onItemClickLister;

    public SubjectListAdapter(Activity activity, List<Subject> subjectList) {
        this.activity = activity;
        this.subjectList = subjectList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
//        La viewDataBinding = DataBindingUtil.inflate(inflater, R.layout.item_adv, parent, false);
        LayoutSubjectItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.layout_subject_item, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Subject subject = getSubject(position);
        holder.bindSubjectItem(subject);
        holder.setImageIcon(FilePathUtil.getImageFromPath(subject.getIconUrl()));
    }

    @Override
    public int getItemCount() {
        return subjectList.size();
    }

    public Subject getSubject(int position) {
        return subjectList.get(position);
    }

    public void setOnItemClickLister(OnItemClickLister clickLister) {
        this.onItemClickLister = clickLister;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements SubjectItemView {
        private LayoutSubjectItemBinding binding;
        ImageView imageView;
        public ViewHolder(LayoutSubjectItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            imageView = (ImageView)binding.getRoot().findViewById(R.id.iv_subject_image);
        }

        public void bindSubjectItem(Subject subject) {
            binding.setSubject(subject);
            binding.setPresenter(new SubjectItemPresenter(activity, this));
            binding.executePendingBindings();

        }

        public void setImageIcon(Bitmap bitmap){
            if(null != bitmap){
                imageView.setImageBitmap(bitmap);
            }else{
                Log.d(TAG, "null image");
            }
        }

        @Override
        public void onDeleteClick() {
            onItemClickLister.onClick(getSubject(getLayoutPosition()), getLayoutPosition());
        }
    }



    public class SubjectItemPresenter {
        Context context;
        SubjectItemView view;

        public SubjectItemPresenter(Context context, SubjectItemView view) {
            this.context = context;
            this.view = view;
        }

        public void onDeleteItemClick() {
            view.onDeleteClick();
        }


    }

    public interface SubjectItemView {
        void onDeleteClick();
    }

    public interface OnItemClickLister {
        void onClick(Subject subject, int position);
    }


}
