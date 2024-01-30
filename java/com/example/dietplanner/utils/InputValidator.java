package com.example.dietplanner.utils;

import android.view.View;
import android.widget.EditText;

public class InputValidator {

    public boolean isEmpty(EditText editText){
        if(editText.getText().toString().trim().equals("")){
            return true;
        }
        return false;
    }
}
