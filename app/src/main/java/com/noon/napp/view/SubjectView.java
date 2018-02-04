package com.noon.napp.view;

import com.noon.napp.model.Subject;

import java.util.List;

/**
 * Created by ril on 4/2/18.
 */

public interface SubjectView {

    void getSubjects(List<Subject> subjectList);
    void deleteSubject(int deletedCount);
}
