package com.mikiandor.welearn.Objects;

public class User {
    private String mail;
    private String id;
    private String name;
    private String teacherSubjectsId;


    public User(String id,String mail , String name) {
        this.mail = mail;
        this.id = id;
        this.name = name;
        teacherSubjectsId = "";
    }

    public User() {
    }


    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeacherSubjectsId() {
        return teacherSubjectsId;
    }

    public void setTeacherSubjectsId(String teacherSubjectsId) {
        this.teacherSubjectsId = teacherSubjectsId;
    }
}
