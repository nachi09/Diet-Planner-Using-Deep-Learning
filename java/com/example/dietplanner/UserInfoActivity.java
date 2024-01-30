package com.example.dietplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.dietplanner.databinding.ActivityUserInfoBinding;
import com.example.dietplanner.models.UserInfoModel;
import com.example.dietplanner.utils.Convertors;
import com.example.dietplanner.utils.InputValidator;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class UserInfoActivity extends AppCompatActivity {




    private ActivityUserInfoBinding binding;
    private UserInfoModel user;
    private InputValidator inputValidator;
    private int caloriesToMaintain;
    private int activityLevel;
    private String gender;

    private String weightGoal;

    ArrayAdapter<String> genderAdapter;

    String[] genderList = {"Male","Female"};

    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserInfoBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        sharedPreferences =getSharedPreferences("user_info",MODE_PRIVATE);

        if(sharedPreferences.getBoolean("detailfilled",true)){
           startActivity(new Intent(UserInfoActivity.this,GenerateDietActivity.class));
        }

        binding.include.imageBtnCustomToolbar.setVisibility(View.INVISIBLE);

        genderAdapter = new ArrayAdapter<>(this,R.layout.option_row,genderList);
        binding.etGenderAutoComplete.setAdapter(genderAdapter);

        binding.etGenderAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick (AdapterView<?> adapterView, View view, int i, long l) {
                String selectedGender = adapterView.getItemAtPosition(i).toString();

                if(selectedGender.equals("Male")){
                    gender="Male";
                }else
                    gender="Female";
            }
        });



        binding.gainLoseToggle.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged (RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.lose:
                        weightGoal="Lose";
                        break;
                    default:
                        weightGoal="Gain";
                        break;

                }
                RadioButton radioButton = radioGroup.findViewById(i);
            }
        });
        binding.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged (RadioGroup radioGroup, int i) {

                switch (i){
                    case R.id.rb_sedentary:
                        activityLevel=1;
                        break;
                    case R.id.rb_little_active:
                        activityLevel=2;
                        break;
                    case R.id.rb_moderateley_active:
                        activityLevel=3;
                        break;
                    case R.id.rb_very_active:
                        activityLevel=4;
                        break;
                    case R.id.rb_extra_active:
                        activityLevel=5;
                        break;

                }
                RadioButton radioButton = radioGroup.findViewById(i);
            }
        });
        binding.buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {

                int selectedId = binding.radioGroup.getCheckedRadioButtonId();
                if (selectedId==-1) {
                    Toast.makeText(UserInfoActivity.this, "Select Activity Level", Toast.LENGTH_SHORT).show();
                    return;
                }

                user = new UserInfoModel();
                setUser(user,binding);
               caloriesToMaintain = Convertors.caloriesToMaintain(user);
               user.setMaintainCalories(caloriesToMaintain);
                //Toast.makeText(UserInfoActivity.this, " "+ user.getCaloriesToMaintain(), Toast.LENGTH_SHORT).show();
               // Toast.makeText(UserInfoActivity.this, Convertors.caloriesToMaintain(user).toString() +" "+user.getGender(), Toast.LENGTH_SHORT).show();

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("detailfilled",true);
                editor.apply();

                updateDetailFilled();

                Intent intent = new Intent(getApplicationContext(),GenerateDietActivity.class);
                intent.putExtra("userInfo",user);
                finish();
                startActivity(intent);


            }
        });



    }

    @Override
    protected void onResume () {
        super.onResume();

        genderAdapter = new ArrayAdapter<>(this,R.layout.option_row,genderList);
        binding.etGenderAutoComplete.setAdapter(genderAdapter);
    }

    public void setUser(UserInfoModel user, ActivityUserInfoBinding binding){

        user.setWeightInKg(Double.parseDouble(binding.etWeightKg.getEditText().getText().toString()));
        user.setHeightInCM(Double.parseDouble(binding.etHeightCm.getEditText().getText().toString()));
        user.setAge(Integer.parseInt(binding.etAge.getEditText().getText().toString()));
        user.setGender(gender);
        user.setActivityLevel(activityLevel);


        SharedPreferences userDetailSP = getSharedPreferences("userDetail",MODE_PRIVATE);
        HashMap<String,String> hashMap = new HashMap<>();

        hashMap.put("weight",binding.etWeightKg.getEditText().getText().toString());
        hashMap.put("height",binding.etHeightCm.getEditText().getText().toString());
        hashMap.put("age",binding.etAge.getEditText().getText().toString());
        hashMap.put("gender",gender);
        hashMap.put("calories",""+Convertors.caloriesToMaintain(user));

        SharedPreferences.Editor editor = userDetailSP.edit();
        editor.putString("weight",binding.etWeightKg.getEditText().getText().toString());
        editor.putString("height",binding.etHeightCm.getEditText().getText().toString());
        editor.putString("age",binding.etAge.getEditText().getText().toString());
        editor.putString("gender",""+ gender);
        editor.putString("calories",Convertors.caloriesToMaintain(user).toString());
        editor.putString("goal",weightGoal);

        editor.commit();
    }


    private void updateDetailFilled(){
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("detailfilled",true);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://dietplanner-b6460-default-rtdb.firebaseio.com/");

        databaseReference.child("users").child(sharedPreferences.getString("contact","")).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess (Object o) {
                Toast.makeText(UserInfoActivity.this, "Detail filled successfully", Toast.LENGTH_SHORT).show();
            }
        });

    }



}