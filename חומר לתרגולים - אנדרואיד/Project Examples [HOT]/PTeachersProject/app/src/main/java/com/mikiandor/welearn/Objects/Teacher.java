package com.mikiandor.welearn.Objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Teacher {
    private String name;
    private String aboutMe;
    private Long price;
    private HashMap<String,String> subjects;
    private String id;
    private String level;

    // rater,rate : max 5 stars
    private HashMap<String,Double> rating;




    public Teacher(String aboutMe, Long price, HashMap<String, String> subjects,String name,String level) {
        this.aboutMe = aboutMe;
        this.price = price;
        this.subjects = subjects;
        this.name = name;
        this.level = level;
        this.rating = new HashMap<>();
    }

    public Teacher() {
    }

    public double averageRate() {
        if(rating == null) rating = new HashMap<>();
        int total = 0;
        for (double value : rating.values()) {
            total += value;
        }
        // floor means we round down the number
        return rating.size () == 0 ? 1 : Math.floor(total / rating.size());
    }

    public boolean hasRatingFromUser(String uid) {
        return this.rating.containsKey(uid);
    }


    public HashMap<String, Double> getRating() {
        return rating;
    }

    public void addRate(String fromUser, double rate) {
        // never happens but just in case database some corrupt teacher
        if(rating == null)  {this.rating = new HashMap<String,Double>(); this.rating.put("Admin",(double) 0);}
        this.rating.put(fromUser,rate);
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
    public void setRate(double rate) {

    }


    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public HashMap<String, String> getSubjects() {
        return subjects;
    }

    public void setSubjects(HashMap<String, String> subjects) {
        this.subjects = subjects;
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

    public void setRating(HashMap<String, Double> rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "name='" + name + '\'' +
                ", aboutMe='" + aboutMe + '\'' +
                ", price=" + price +
                ", subjects=" + subjects +
                ", id='" + id + '\'' +
                ", level='" + level + '\'' +
                ", rating=" + rating +
                '}';
    }
}
