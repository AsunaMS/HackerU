package com.mikiandor.welearn.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.mikiandor.welearn.Auth.Auth_FireBase;
import com.mikiandor.welearn.Data.FireBase_RealTime;
import com.mikiandor.welearn.Objects.UserChat;
import com.mikiandor.welearn.R;

import static com.mikiandor.welearn.Data.FireBase_RealTime.TEACHER_SUBJECT_ID_PATH;

import java.util.ArrayList;

public class Activity_homePage extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
//        Intent i = new Intent(this, Activity_buildTeacherProfile.class);
//        startActivity(i);
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        showChatNewMessages(menu);
        return true;
    }



    private void showChatNewMessages(Menu menu) {
        MenuItem chat_item = menu.getItem(0).getSubMenu().getItem(2);
        FireBase_RealTime.checkUserUnreadMessages(Auth_FireBase.getCurrentUserId(), unread -> {
            chat_item.setIcon(unread ? R.drawable.ic_baseline_new_releases_24 : R.drawable.ic_baseline_mark_chat_unread_24);
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.become_a_teacher:
                openBuildTeacherProfileActivity();
                return true;
                case R.id.search_teacher:
                openSearchTeacherActivity();
                return true;
                case R.id.my_chats:
                    item.setIcon(R.drawable.ic_baseline_mark_chat_unread_24);
                    openMyChats();
                return true;
                case
                    R.id.logout:
                    logOut();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void logOut() {
        Auth_FireBase.logOut();
        finish();
    }

    private void openMyChats() {
        Intent i=new Intent(this,Activity_MyChats.class);
        startActivity(i);
    }

    private void openSearchTeacherActivity() {
        startActivity(new Intent(this,Activity_SearchTeacher.class));
    }

    private void openBuildTeacherProfileActivity() {
        Intent i=new Intent(this,Activity_buildTeacherProfile.class);
        startActivity(i);
    }

    public void onClickAboutMe(View view) {
        startActivity(new Intent(this, Activity_AboutMe.class));
    }

    public void onClickContact(View view) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("mailto:" + "miki66762@gmail.com"));

        try {
            startActivity(Intent.createChooser(emailIntent, "Send email using..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(Activity_homePage.this, "No email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }
}