package com.mikiandor.welearn.Data;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mikiandor.welearn.Auth.Auth_FireBase;
import com.mikiandor.welearn.Interfaces.Auth_Interfaces;
import com.mikiandor.welearn.Objects.ChatMessage;
import com.mikiandor.welearn.Objects.Teacher;
import com.mikiandor.welearn.Objects.User;
import com.mikiandor.welearn.Objects.UserChat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

public class FireBase_RealTime {
    public static final String USER_PATH = "users";
    public static final String CHATS_PATH = "chats";
    public static final String NAME_PATH = "name";
    public static final String TEACHERS_PATH = "teachers";
    public static final String TEACHER_SUBJECT_ID_PATH = "teacherSubjectsId";
    private static DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    public static synchronized DatabaseReference getReference() {
        return databaseReference;
    }


    public interface CallBack_save {
        void onSuccess();

        void onFailure();
    }

    public interface CallBack_readTeachers {
        void onSuccess(ArrayList<Teacher> teacherArrayList);

        void onFailure();
    }

    public interface CallBack_readUserField {
        void onSuccess(String value);

        void onFailure();
    }

    public interface CallBack_isTeacher {
        void onSuccess(boolean isTeacher);

        void onFailure();
    }

    public interface CallBack_readTeacher {
        void onSuccess(Teacher teacher);

        void onFailure();
    }

    public interface CallBack_userChats {
        void onSuccess(ArrayList<UserChat> userChatArrayList);

        void onFailure();
    }


    //Save
    public static void saveNewUser(User user, CallBack_save callBack_save) {
        getReference().child(USER_PATH).child(Auth_FireBase.getCurrentUserId()).setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                callBack_save.onSuccess();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                callBack_save.onFailure();

            }
        });

    }

    public static void saveNewTeacher(HashSet<String> subjectsHashMap, Long price, String aboutMe, String level, CallBack_save callBack_save) {
        HashMap<String, String> subjects = new HashMap<>();
        int i = 1;
        for (String subject : subjectsHashMap) {
            subjects.put(i + "_number", subject);
            i++;
        }

        readUserInfoByField(Auth_FireBase.getCurrentUserId(), NAME_PATH, new CallBack_readUserField() {
            @Override
            public void onSuccess(String name) {
                Teacher teacher = new Teacher(aboutMe, price, subjects, name, level);
                teacher.setId(Auth_FireBase.getCurrentUserId());
                getReference().child(TEACHERS_PATH).child(Auth_FireBase.getCurrentUserId()).setValue(teacher).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        getReference().child(USER_PATH).child(Auth_FireBase.getCurrentUserId()).child(TEACHER_SUBJECT_ID_PATH).setValue("yes");
                        callBack_save.onSuccess();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callBack_save.onFailure();
                    }
                });
            }

            @Override
            public void onFailure() {
                callBack_save.onFailure();
            }
        });


    }


    public static synchronized DatabaseReference getChatReference(String idUserTo, String idUserFrom) {
        String chatId;
        if (idUserFrom.compareTo(idUserTo) < 0) {
            chatId = idUserFrom + "_" + idUserTo;
        } else {
            chatId = idUserTo + "_" + idUserFrom;
        }
        return getReference().child(CHATS_PATH).child(chatId);
    }

    // A Method to reset teacher's rating. delete this after testing
    public static void populateTeachersWithZeroRatings() {
        getReference().child(TEACHERS_PATH).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot sh : snapshot.getChildren()){
                    if(sh.child("rating").getValue() == null) {
                        HashMap<String,Double> rating = new HashMap<String,Double>();
                        rating.put("Admin",0.0);
                        sh.getRef().child("rating").setValue(rating);
                    } else {
                        Teacher t = sh.getValue(Teacher.class);
                        if (t == null) continue;
                        HashMap<String, Double> rating = new HashMap<String, Double>();
                        rating.put("Admin", 0.0);
                        sh.getRef().child("rating").setValue(rating);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
    }

    public interface UserHasChatWithTeacher {
        void result(boolean has);
    }

    public static void changeTeacherRead() {
        getReference().child(CHATS_PATH).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot sh :  snapshot.getChildren()) {
                 for(DataSnapshot s: sh.getChildren()) {
                     s.child("read").getRef().setValue(false);
                 }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public static void CheckUserHasChatWithTeacher(String uid, String tid, UserHasChatWithTeacher callback) {
        getChatReference(uid, tid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                callback.result(snapshot.getChildren().iterator().hasNext());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.result(false);
            }
        });
    }


    public static void sendMessage(String idUserTo, ChatMessage chatMessage, CallBack_save callBack_save) {

        String idUserFrom = chatMessage.getUserId();
        getChatReference(idUserTo, idUserFrom).push().setValue(chatMessage).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                callBack_save.onSuccess();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                callBack_save.onFailure();

            }
        });


    }



    public static void addRatingToTeacher(String fromUser,String teacherId,double rate) {
        getReference().child(TEACHERS_PATH).child(teacherId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Teacher teacher = snapshot.getValue(Teacher.class);
                if(teacher  == null) {
                    Log.d("Debug/ ","Teacher is null");
                    return;}
                teacher.addRate(fromUser,rate);
                snapshot.getRef().setValue(teacher);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println(error.getMessage());
            }
        });
    }


    //Read
    public static void readTeachers(String subject,CallBack_readTeachers callBack_readTeachers)
    {
        ArrayList<Teacher> teacherArrayList = new ArrayList<>();
        getReference().child(TEACHERS_PATH)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    //מערך שיכיל את id של החניות שהמשתמש שייך אליהם

                    @Override

                    public void onDataChange(@NonNull DataSnapshot  dataSnapshot ) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Teacher teacher = snapshot.getValue(Teacher.class);
                            if(teacher == null) continue;
                            for(String value: teacher.getSubjects().values())
                            {
                                if(value.equals(subject)) {
                                    teacherArrayList.add(teacher);
                                    break;
                                }
                            }

                        }
                        callBack_readTeachers.onSuccess(teacherArrayList);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        callBack_readTeachers.onFailure();
                    }
                });
    }

    public static void readUserInfoByField(String userId,String field,CallBack_readUserField callBack_readUserField)
    {
        getReference().child(USER_PATH).child(userId).child(field).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() ==  null) return;
                callBack_readUserField.onSuccess(snapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callBack_readUserField.onFailure();
            }
        });
    }
    

    public static void readTeacherInfo(String teacherId,CallBack_readTeacher callBack_readTeacher)
    {
        getReference().child(TEACHERS_PATH).child(teacherId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                callBack_readTeacher.onSuccess(snapshot.getValue(Teacher.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callBack_readTeacher.onFailure();
            }
        });

    }


    /**
     ** Checks if the user has unread messages
     *  message is marked on firebase as read = false
     *  means the user hasn't viewed the chat yet
     */

    public interface UserUnreadMessagesCallback {
        void unreadMessage(boolean unread);
    }
    public static void checkUserUnreadMessages(String userId,UserUnreadMessagesCallback callback)
    {
        new Thread(() -> {
            getReference().child(CHATS_PATH).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        for(DataSnapshot s : dataSnapshot.getChildren()) {
                            String uid = ((String) s.child("userId").getValue());
                            if(uid == null) continue;
                            if(uid.contains(userId)) {
                                if(Objects.equals(s.child("read").getValue(), false)) {
                                    callback.unreadMessage(true);
                                }
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    System.out.println(error.getMessage());
                }
            });
        }).start();
    }




    public static void readAllUsersChats(String userId,CallBack_userChats callBack_userChats)
    {
        getReference().child(CHATS_PATH).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<UserChat> userChatArrayList = new ArrayList<>();
                int count =0;
                for(DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    count++;
                }
                final int totalChats = count;
                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String otherUserId = dataSnapshot.getKey();
                    otherUserId =  otherUserId.replace(userId, "");
                    otherUserId =  otherUserId.replace("_", "");
                    final String finalOtherUserId = otherUserId;
                    readUserInfoByField(finalOtherUserId,NAME_PATH, new CallBack_readUserField() {
                        @Override
                        public void onSuccess(String name) {
                            userChatArrayList.add(new UserChat(name, finalOtherUserId));
                            if (userChatArrayList.size() > 0) {
                                callBack_userChats.onSuccess(userChatArrayList);
                            }
                        }

                        @Override
                        public void onFailure() {
                        System.err.println("Fail");
                        }
                    });
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callBack_userChats.onFailure();
            }
        });

    }


}
