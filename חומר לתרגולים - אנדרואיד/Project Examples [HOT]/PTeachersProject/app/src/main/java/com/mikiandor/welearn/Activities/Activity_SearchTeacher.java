package com.mikiandor.welearn.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.mikiandor.welearn.Adapters.Adapter_Teacher;
import com.mikiandor.welearn.Adapters.TeacherItemDelegate;
import com.mikiandor.welearn.Data.FireBase_RealTime;
import com.mikiandor.welearn.Objects.Teacher;
import com.mikiandor.welearn.R;
import com.mikiandor.welearn.finals.Finals;

import java.util.ArrayList;

public class Activity_SearchTeacher extends AppCompatActivity {

    private Spinner searchTeacher_SPINNER_subjects;
    private ListView searchTeacher_LV_teachers;
    private Adapter_Teacher adapter_teacher;
    private int pos;
    public final static String USER_CHAT_WITH_EXTRAS = "USER_CHAT_WITH_EXTRAS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_teacher);
        findViews();
        initViews();

    }

    private void initViews() {
        initSubjectsSpinner();
    }
    private void refreshList(){
        FireBase_RealTime.readTeachers(Finals.SUBJECTS[pos].trim(), new FireBase_RealTime.CallBack_readTeachers() {
            @Override
            public void onSuccess(ArrayList<Teacher> teacherArrayList) {
                for(Teacher teacher: teacherArrayList)
                {
                    Log.d("mikior55", "onSuccess: "+teacher);
                }
                setTeacherAdapter(teacherArrayList);
            }
            @Override
            public void onFailure() {

            }
        });
    }


    private void initSubjectsSpinner() {

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, Finals.SUBJECTS);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        searchTeacher_SPINNER_subjects.setAdapter(adapter);
        searchTeacher_SPINNER_subjects.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                if(Finals.SUBJECTS[position].trim().isEmpty())
                    return;
                pos = position;
                FireBase_RealTime.readTeachers(Finals.SUBJECTS[position].trim(), new FireBase_RealTime.CallBack_readTeachers() {
                    @Override
                    public void onSuccess(ArrayList<Teacher> teacherArrayList) {
                        for(Teacher teacher: teacherArrayList)

                        {
                            Log.d("mikior55", "onSuccess: "+teacher);
                        }
                        setTeacherAdapter(teacherArrayList);
                    }

                    @Override
                    public void onFailure() {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
    }

    private void setTeacherAdapter(ArrayList<Teacher> arrayList) {
        adapter_teacher = new Adapter_Teacher(this, R.layout.teacher_item, arrayList, this::refreshList);
        searchTeacher_LV_teachers .setAdapter(adapter_teacher);
    }

    private void findViews() {
        searchTeacher_SPINNER_subjects = findViewById(R.id.searchTeacher_SPINNER_subjects);
        searchTeacher_LV_teachers = findViewById(R.id.searchTeacher_LV_teachers);
    }
}