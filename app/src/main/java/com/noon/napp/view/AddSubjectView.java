package com.noon.napp.view;

import com.noon.napp.model.Subject;

/**
 * Created by ril on 4/2/18.
 */

public interface AddSubjectView {

    void onSubmitButtonClick(Subject subject);
    void onGalleryBtnClick();
    void onError(String error);
}
