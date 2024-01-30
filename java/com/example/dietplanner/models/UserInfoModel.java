package com.example.dietplanner.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "user")
public class UserInfoModel implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "contact")
    private String contact;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "weightinkg")
    private double weightInKg;
    @ColumnInfo(name = "heightincm")
    private double heightInCM;
    @ColumnInfo(name = "age")
    private int age;
    @ColumnInfo(name = "activitylevel")
    private int activityLevel;
    @ColumnInfo(name = "gender")
    private String gender;
    @ColumnInfo(name = "maintaincalories")
    private int maintainCalories;
    @ColumnInfo(name = "goal")
    private int goal=0;
    @ColumnInfo(name = "goalweight")
    private int goalWeight;


    public String getContact () {
        return contact;
    }

    public void setContact (String contact) {
        this.contact = contact;
    }

    public UserInfoModel (int id, String contact, String name, double weightInKg, double heightInCM, int age, int activityLevel, String gender, int maintainCalories, int goal, int goalWeight) {
        this.id = id;
        this.contact = contact;
        this.name = name;
        this.weightInKg = weightInKg;
        this.heightInCM = heightInCM;
        this.age = age;
        this.activityLevel = activityLevel;
        this.gender = gender;
        this.maintainCalories = maintainCalories;
        this.goal = goal;
        this.goalWeight = goalWeight;
    }

    @Ignore
    public UserInfoModel (String name, double weightInKg, double heightInCM, int age, int activityLevel, String gender, int maintainCalories, int goal, int goalWeight) {

        this.name = name;
        this.weightInKg = weightInKg;
        this.heightInCM = heightInCM;
        this.age = age;
        this.activityLevel = activityLevel;
        this.gender = gender;
        this.maintainCalories = maintainCalories;
        this.goal = goal;
        this.goalWeight = goalWeight;
    }

    @Ignore
    public UserInfoModel (String name, double weightInKg, double heightInCM, int age, int activityLevel, String gender) {
        this.name = name;
        this.weightInKg = weightInKg;
        this.heightInCM = heightInCM;
        this.age = age;
        this.activityLevel = activityLevel;
        this.gender = gender;
    }

    public int getGoal () {
        return goal;
    }

    public void setGoal (int goal) {
        this.goal = goal;
    }

    public int getGoalWeight () {
        return goalWeight;
    }

    public void setGoalWeight (int goalWeight) {
        this.goalWeight = goalWeight;
    }

    public UserInfoModel () {

    }

    public int getMaintainCalories () {
        return maintainCalories;
    }
    public int getId () {
        return id;
    }

    public void setId (int id) {
        this.id = id;
    }

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public double getWeightInKg () {
        return weightInKg;
    }

    public void setWeightInKg (double weightInKg) {
        this.weightInKg = weightInKg;
    }

    public double getHeightInCM () {
        return heightInCM;
    }

    public void setHeightInCM (double heightInCM) {
        this.heightInCM = heightInCM;
    }

    public int getAge () {
        return age;
    }

    public void setMaintainCalories (int maintainCalories) {
        this.maintainCalories = maintainCalories;
    }

    public void setAge (int age) {
        this.age = age;
    }

    public int getActivityLevel () {
        return activityLevel;
    }

    public void setActivityLevel (int activityLevel) {
        this.activityLevel = activityLevel;
    }

    public String getGender () {
        return gender;
    }

    public void setGender (String gender) {
        this.gender = gender;
    }


}
