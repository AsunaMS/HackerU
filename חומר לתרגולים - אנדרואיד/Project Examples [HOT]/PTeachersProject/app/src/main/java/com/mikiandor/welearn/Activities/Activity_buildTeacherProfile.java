package com.mikiandor.welearn.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mikiandor.welearn.Auth.Auth_FireBase;
import com.mikiandor.welearn.Data.FireBase_RealTime;
import com.mikiandor.welearn.Objects.Teacher;
import com.mikiandor.welearn.R;
import com.mikiandor.welearn.finals.Finals;

import java.util.HashSet;

import static com.mikiandor.welearn.Data.FireBase_RealTime.TEACHER_SUBJECT_ID_PATH;

public class Activity_buildTeacherProfile extends AppCompatActivity {

    private TextView buildTeacherProfile_TextView_subjects;
    private TextView buildTeacherProfile_EditText_aboutMe;
    private TextView buildTeacherProfile_EditText_price;
    private TextView buildTeacherProfile_BTN_save;


    private Spinner buildTeacherProfile_SPINNER_subjects;
    private HashSet<String> subjectsHashSet;
    private Spinner searchTeacher_SPINNER_level;

    private String subjectsStr;

    public Activity_buildTeacherProfile() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_build_teacher_profile);
        initObjects();
        findViews();
        initViews();
        FireBase_RealTime.readUserInfoByField(Auth_FireBase.getCurrentUserId(), TEACHER_SUBJECT_ID_PATH, new FireBase_RealTime.CallBack_readUserField() {
            @Override
            public void onSuccess(String value) {
                if (value != null && !value.isEmpty()) {
                    loadDataFromDataBase();
                }
            }

            @Override
            public void onFailure() {

            }
        });
    }

    private void loadDataFromDataBase() {
        FireBase_RealTime.readTeacherInfo(Auth_FireBase.getCurrentUserId(), new FireBase_RealTime.CallBack_readTeacher() {
            @Override
            public void onSuccess(Teacher teacher) {
                subjectsStr = "";
                for (String subject : teacher.getSubjects().values()) {
                    subjectsHashSet.add(subject);
                    subjectsStr += subject + ", ";
                }
                if (subjectsStr.length() >= 2) {
                    subjectsStr = subjectsStr.substring(0, subjectsStr.length() - 2);
                }
                buildTeacherProfile_TextView_subjects.setText(subjectsStr);

                buildTeacherProfile_EditText_aboutMe.setText(teacher.getAboutMe());
                buildTeacherProfile_EditText_price.setText(teacher.getPrice() + "");

                for(int i=0;i<Finals.LEVELS.length;i++)
                {
                    if(teacher.getLevel().equals(Finals.LEVELS[i])) {
                        searchTeacher_SPINNER_level.setSelection(i);
                        return;
                    }

                }

            }

            @Override
            public void onFailure() {

            }
        });
    }

    private void initObjects() {
        subjectsHashSet = new HashSet<>();
        subjectsStr = "";
    }

    private void initViews() {
        initSpinnerSubjects();
        buildTeacherProfile_BTN_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSave();
            }
        });
        initLevelSpinner();


    }

    private void initSpinnerSubjects() {


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, Finals.SUBJECTS);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        buildTeacherProfile_SPINNER_subjects.setAdapter(adapter);


        buildTeacherProfile_SPINNER_subjects.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (subjectsHashSet.contains(Finals.SUBJECTS[position])) {
                    subjectsHashSet.remove(Finals.SUBJECTS[position]);
                    Log.d("ormiki_62", "onItemSelected: ");
                } else {
                    if (!Finals.SUBJECTS[position].isEmpty()) {
                        subjectsHashSet.add(Finals.SUBJECTS[position]);
                        Log.d("ormiki_67", "onItemSelected: ");
                    }

                }
                subjectsStr = "";
                for (String subject : subjectsHashSet) {
                    subjectsStr += subject + ", ";
                    Log.d("ormiki_64", "onItemSelected: " + subject);
                }
                if (subjectsStr.length() >= 2) {
                    subjectsStr = subjectsStr.substring(0, subjectsStr.length() - 2);
                    Log.d("ormiki_78", "onItemSelected: ");

                }

                buildTeacherProfile_TextView_subjects.setText(subjectsStr);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
    }

    private void findViews() {
        buildTeacherProfile_SPINNER_subjects = findViewById(R.id.searchTeacher_SPINNER_subjects);
        searchTeacher_SPINNER_level = findViewById(R.id.searchTeacher_SPINNER_level);
        buildTeacherProfile_TextView_subjects = findViewById(R.id.buildTeacherProfile_TextView_subjects);
        buildTeacherProfile_EditText_aboutMe = findViewById(R.id.buildTeacherProfile_EditText_aboutMe);
        buildTeacherProfile_EditText_price = findViewById(R.id.buildTeacherProfile_EditText_price);
        buildTeacherProfile_BTN_save = findViewById(R.id.buildTeacherProfile_BTN_save);
    }

    private void onClickSave() {

        if (checkFields())
            FireBase_RealTime.saveNewTeacher(subjectsHashSet, Long.parseLong(buildTeacherProfile_EditText_price.getText().toString()), buildTeacherProfile_EditText_aboutMe.getText().toString(), searchTeacher_SPINNER_level.getSelectedItem().toString(), new FireBase_RealTime.CallBack_save() {
                @Override
                public void onSuccess() {
                    Toast.makeText(Activity_buildTeacherProfile.this, "You are a teacher!!!!", Toast.LENGTH_SHORT).show();
                    finish();
                }

                @Override
                public void onFailure() {

                }
            });
    }

    private boolean checkFields() {
        if (subjectsHashSet.isEmpty()) {
            Toast.makeText(this, "fill subjects!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (buildTeacherProfile_EditText_price.getText().toString().trim().isEmpty()) {

            Toast.makeText(this, "fill price!", Toast.LENGTH_SHORT).show();
            return false;
        }


        if (buildTeacherProfile_EditText_aboutMe.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "fill about me!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (searchTeacher_SPINNER_level.getSelectedItem().toString().trim().isEmpty()) {
            Toast.makeText(this, "fill level!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void initLevelSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, Finals.LEVELS);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        searchTeacher_SPINNER_level.setAdapter(adapter);

    }
}