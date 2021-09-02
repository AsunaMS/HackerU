package com.mikiandor.welearn.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.mikiandor.welearn.Auth.Auth_FireBase;
import com.mikiandor.welearn.Data.FireBase_RealTime;
import com.mikiandor.welearn.Objects.UserChat;
import com.mikiandor.welearn.R;

import java.util.ArrayList;

public class Activity_MyChats extends AppCompatActivity {

    private ListView myChats_LV_usersChats;

    private  ArrayList<UserChat> userChatArrayList;
    private ArrayAdapter<String> arrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_chats);
        findViews();
        initViews();

    }

    private void initViews() {
        initListView();
    }

    private void initListView() {
        new Thread( () -> {
            FireBase_RealTime.readAllUsersChats(Auth_FireBase.getCurrentUserId(), new FireBase_RealTime.CallBack_userChats() {
                @Override
                public void onSuccess(ArrayList<UserChat> result) {
                    userChatArrayList = result;
                    ArrayList<String> names = new ArrayList<>();
                    for(UserChat userChat : userChatArrayList)
                    {
                        names.add(userChat.getName());
                    }
                    arrayAdapter = new ArrayAdapter<String>(Activity_MyChats.this,
                            android.R.layout.simple_list_item_1, names);
                    runOnUiThread(() -> {
                        myChats_LV_usersChats.setAdapter(arrayAdapter);
                        myChats_LV_usersChats.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent i=new Intent(Activity_MyChats.this, Activity_Chat.class);
                                i.putExtra(Activity_SearchTeacher.USER_CHAT_WITH_EXTRAS,userChatArrayList.get(position).getId());
                                startActivity(i);
                            }
                        });
                    });
                }

                @Override
                public void onFailure() {
                    System.err.println("Failed to load chat");
                }
            });
        }).start();

    }

    private void findViews() {
        myChats_LV_usersChats = findViewById(R.id.myChats_LV_usersChats);
    }

}