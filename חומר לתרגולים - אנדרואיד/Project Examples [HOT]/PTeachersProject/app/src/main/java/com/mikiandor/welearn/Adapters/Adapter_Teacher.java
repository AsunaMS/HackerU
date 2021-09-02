package com.mikiandor.welearn.Adapters;

import android.app.Activity;
import androidx.fragment.app.FragmentManager;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;

import com.google.android.gms.auth.api.Auth;
import com.mikiandor.welearn.Activities.Activity_Chat;
import com.mikiandor.welearn.Activities.Activity_Login;
import com.mikiandor.welearn.Activities.Activity_Register;
import com.mikiandor.welearn.Activities.Activity_SearchTeacher;
import com.mikiandor.welearn.Auth.Auth_FireBase;
import com.mikiandor.welearn.Data.FireBase_RealTime;
import com.mikiandor.welearn.Objects.Teacher;
import com.mikiandor.welearn.Objects.User;
import com.mikiandor.welearn.Objects.UserChat;
import com.mikiandor.welearn.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Adapter_Teacher extends ArrayAdapter<Teacher> {

    private int resourceLayout;
    private Context mContext;
    private TeacherItemDelegate delegate;
    public Adapter_Teacher(Context context, int resource, List<Teacher> items,TeacherItemDelegate delegate) {
        super(context, resource, items);
        this.resourceLayout = resource;
        this.mContext = context;
        this.delegate = delegate;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(mContext);
            v = vi.inflate(resourceLayout, null);
        }

        Teacher teacher = getItem(position);

        if (teacher != null) {
            TextView teacherItem_TV_name = (TextView) v.findViewById(R.id.teacherItem_TV_name);
            TextView teacherItem_TV_subjects = (TextView) v.findViewById(R.id.teacherItem_TV_subjects);
            TextView teacherItem_TV_price = (TextView) v.findViewById(R.id.teacherItem_TV_price);
            TextView teacherItem_TV_aboutMe = (TextView) v.findViewById(R.id.teacherItem_TV_aboutMe);
            TextView teacherItem_TV_level = (TextView) v.findViewById(R.id.teacherItem_TV_level);
            CardView teacherItem_Rate_Button_Holder = v.findViewById(R.id.button_rate_holder);
            Button teacherItem_Rate_Button = v.findViewById(R.id.button_rate);
            Button teacherItem_BTN_contactMe =  v.findViewById(R.id.teacherItem_BTN_contactMe);
            ImageView[] stars = {v.findViewById(R.id.teacherItemRateStar1),v.findViewById(R.id.teacherItemRateStar2),
                    v.findViewById(R.id.teacherItemRateStar3),v.findViewById(R.id.teacherItemRateStar4),
                    v.findViewById(R.id.teacherItemRateStar5)};



            // Check if the user can rate the teacher
            if(teacher.hasRatingFromUser(Auth_FireBase.getCurrentUserId())) {
                teacherItem_Rate_Button_Holder.setVisibility(View.INVISIBLE);
            }else {
                FireBase_RealTime.CheckUserHasChatWithTeacher(Auth_FireBase.getCurrentUserId(), teacher.getId(), has -> {
                    teacherItem_Rate_Button_Holder.setVisibility(has ? View.VISIBLE : View.INVISIBLE);
                });
            }


            teacherItem_Rate_Button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    View dView = LayoutInflater.from(getContext()).inflate(R.layout.rating_dialog,null,false);
                    // opens the rating dialog
                    AlertDialog dialog =  new AlertDialog.Builder(getContext()).setIcon(R.drawable.ic_baseline_star_rate_24).setTitle("Rate " + teacher.getName()).setView(dView).create();
                    RatingBar ratingBar = dView.findViewById(R.id.rating_bar);
                    ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                        @Override
                        public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                            // Add a rating for the teacher, refresh the list view
                            FireBase_RealTime.addRatingToTeacher(Auth_FireBase.getCurrentUserId(),teacher.getId(), v);
                            dialog.dismiss();
                            delegate.refreshTeacherList();
                        }
                    });
                    dialog.show();
                }
            });

            // Apply teacher rating by filling stars
            for(int i = 0; i < teacher.averageRate(); i ++) {
                Log.d("Debug/ ",teacher.averageRate() + "");
                stars[i].setImageResource(R.drawable.ic_baseline_star_rate_24);
            }

            teacherItem_BTN_contactMe.setOnClickListener(v1 -> {
                Intent i = new Intent(mContext, Activity_Chat.class);
                i.putExtra(Activity_SearchTeacher.USER_CHAT_WITH_EXTRAS,teacher.getId());
                mContext.startActivity(i);
            });

            if (teacherItem_TV_name != null) {
                teacherItem_TV_name.setText(teacher.getName());
            }

            if (teacherItem_TV_subjects != null) {
                String subjects ="";
                for(String subject: teacher.getSubjects().values())
                {
                    subjects+=subject+", ";
                }
                subjects = subjects.substring(0,subjects.length()-2);
                teacherItem_TV_subjects.setText(subjects);
            }

            if (teacherItem_TV_price != null) {
                teacherItem_TV_price.setText(teacher.getPrice()+"");
            }
            if (teacherItem_TV_aboutMe != null) {
                teacherItem_TV_aboutMe.setText(teacher.getAboutMe());
            }
            if (teacherItem_TV_level != null) {
                teacherItem_TV_level.setText(teacher.getLevel());
            }
        }

        return v;
    }

}
