package com.mikiandor.welearn.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mikiandor.welearn.Auth.Auth_FireBase;
import com.mikiandor.welearn.Data.FireBase_RealTime;
import com.mikiandor.welearn.Objects.ChatMessage;
import com.mikiandor.welearn.R;

public class Activity_Chat extends AppCompatActivity {

    private ImageView chat_IMAGEVIEW_send;
    private EditText chat_ET_input;
    private ListView chat_LV_messages;
    private String userIdChatWith;
    private FirebaseListAdapter<ChatMessage> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        readExtras();
        findViews();
        initViews();

    }

    private void readExtras() {
        userIdChatWith = getIntent().getStringExtra(Activity_SearchTeacher.USER_CHAT_WITH_EXTRAS);
    }

    private void initViews() {



        chat_IMAGEVIEW_send.setOnClickListener(v -> {
            if(checkFields(chat_ET_input.getText().toString().trim()))
                sendMessage();
        });

        initListView();
    }

    private void initListView() {
        adapter = new FirebaseListAdapter<ChatMessage>(this, ChatMessage.class,
                R.layout.message, FireBase_RealTime.getChatReference(userIdChatWith,Auth_FireBase.getCurrentUserId())) {
            @Override
            protected void populateView(View v, ChatMessage model, int position) {
                // Get references to the views of message.xml
                TextView messageText = (TextView)v.findViewById(R.id.message_text);
                TextView messageUser = (TextView)v.findViewById(R.id.message_user);
                TextView messageTime = (TextView)v.findViewById(R.id.message_time);

                // Set their text
                messageText.setText(model.getMessageText());
                messageUser.setText(model.getUserName());

                // Format the date before showing it
                messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)",
                        model.getMessageTime()));
                FireBase_RealTime.getChatReference(userIdChatWith,Auth_FireBase.getCurrentUserId()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot s : snapshot.getChildren())
                        {
                            System.err.println("Read!!!");
                            if(s == null || s.getKey() ==  null)return;
                            FireBase_RealTime.getChatReference(userIdChatWith,Auth_FireBase.getCurrentUserId()).child(s.getKey()).child("read").setValue(true);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        };
        chat_LV_messages.setAdapter(adapter);
    }

    private boolean checkFields(String message) {
        if(message.isEmpty())
            return false;
        return true;
    }

    private void sendMessage() {
        FireBase_RealTime.readUserInfoByField(Auth_FireBase.getCurrentUserId(),FireBase_RealTime.NAME_PATH, new FireBase_RealTime.CallBack_readUserField() {
            @Override
            public void onSuccess(String name) {
                ChatMessage chatMessage = new ChatMessage( chat_ET_input.getText().toString(),  name, Auth_FireBase.getCurrentUserId());
                chatMessage.setRead(false);
                FireBase_RealTime.sendMessage(userIdChatWith, chatMessage, new FireBase_RealTime.CallBack_save() {
                    @Override
                    public void onSuccess() {
                        chat_ET_input.setText("");
                    }

                    @Override
                    public void onFailure() {

                    }
                });
            }
            @Override
            public void onFailure() {

            }
        });
    }

    private void findViews() {
        chat_IMAGEVIEW_send= findViewById(R.id.chat_IMAGEVIEW_send);
                chat_ET_input= findViewById(R.id.chat_ET_input);
        chat_LV_messages= findViewById(R.id.chat_LV_messages);
    }
}