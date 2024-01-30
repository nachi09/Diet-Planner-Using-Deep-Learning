package com.example.dietplanner.ui.profile;

import android.content.Intent;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ProfileViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    private final MutableLiveData<Integer> mWeight;


    public ProfileViewModel () {

        mText = new MutableLiveData<>();
        mWeight = new MutableLiveData<>();
        mText.setValue("NachiKet Pawar");


    }

    public LiveData<String> getText () {
        return mText;
    }
    public LiveData<Integer> getWeight (){return  mWeight;}
}