package com.example.dietplanner;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.dietplanner.models.FoodItemModel;
import com.example.dietplanner.models.UserInfoModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.dietplanner.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupWithNavController(binding.navView, navController);



//        Intent intent = getIntent();
//        UserInfoModel user = (UserInfoModel) intent.getSerializableExtra("userInfo");
//        Intent intent = getIntent();
//        UserInfoModel user = (UserInfoModel) intent.getSerializableExtra("userInfo");
//
//
//        Toast.makeText(this, user.getActivityLevel(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed () {
        super.onBackPressed();
        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory( Intent.CATEGORY_HOME );
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);
    }
}