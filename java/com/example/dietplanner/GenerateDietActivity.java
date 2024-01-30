package com.example.dietplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Shader;
import android.os.Bundle;
import android.view.View;

import com.example.dietplanner.databinding.ActivityGenerateDietBinding;
import com.example.dietplanner.models.UserInfoModel;
import com.example.dietplanner.utils.Convertors;

public class GenerateDietActivity extends AppCompatActivity {

    SharedPreferences userDetail,sharedPreferences;
    private UserInfoModel user;
    private String protein,carbs,fats,calories;
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        com.example.dietplanner.databinding.ActivityGenerateDietBinding binding = ActivityGenerateDietBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        sharedPreferences = getSharedPreferences("user_info",MODE_PRIVATE);
        userDetail = getSharedPreferences("userDetail",MODE_PRIVATE);

        calories = userDetail.getString("calories","");
        String goalCalories= Convertors.calorieAccToGoal(calories,userDetail.getString("goal","Gain"));

        binding.tvName.setText(sharedPreferences.getString("firstname",""));
        binding.generateCalories.setText(goalCalories);
        carbs = Convertors.requiredCarbs(goalCalories);
        binding.tvCarbs.setText(carbs);

        protein =Convertors.requiredProtein(goalCalories);
        binding.tvProtein.setText(protein);

        fats = Convertors.requiredFats(goalCalories);
        binding.tvFats.setText(fats);


//        Intent intent = getIntent();
//        UserInfoModel user = (UserInfoModel)intent.getSerializableExtra("userInfo");

        binding.generateCalories.setText(goalCalories);

        binding.buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
               // intent.putExtra("userInfo",user);
                finish();
                startActivity(intent);
            }
        });


    }
}