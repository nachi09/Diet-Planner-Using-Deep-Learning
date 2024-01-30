package com.example.dietplanner.ui.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dietplanner.MainActivity;
import com.example.dietplanner.R;
import com.example.dietplanner.adapters.FoodListRecyclerViewAdapter;
import com.example.dietplanner.databinding.FragmentHomeBinding;
import com.example.dietplanner.models.FoodItemModel;
import com.example.dietplanner.models.UserInfoModel;
import com.example.dietplanner.ui.loginregister.LoginActivity;
import com.example.dietplanner.utils.Convertors;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;

public class HomeFragment extends Fragment {


    String goalCalories;
    SharedPreferences sharedPreferences,userDetail;
    private FragmentHomeBinding binding;

    GoogleSignInOptions googleSignInOptions;
    GoogleSignInClient googleSignInClient;

    public View onCreateView (@NonNull LayoutInflater inflater,
                              ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        userDetail =getActivity().getSharedPreferences("userDetail",Context.MODE_PRIVATE);



        sharedPreferences = getActivity().getSharedPreferences("user_info", Context.MODE_PRIVATE);
        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleSignInClient = GoogleSignIn.getClient(requireContext(),googleSignInOptions);

        goalCalories = Convertors.calorieAccToGoal(userDetail.getString("calories","2151"),userDetail.getString("goal","Gain"));

    binding.tvdailyCalories.setText(goalCalories);


        setRecyclerView(root);

        binding.btnHomeFragmentSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {

                if(sharedPreferences.contains("contact")){
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();
                    startActivity(new Intent((Activity)getActivity(), LoginActivity.class));
                    
                }else{
                    signout();
                }
                Toast.makeText(getActivity(), "Logout Successful", Toast.LENGTH_SHORT).show();
            }
        });


//       Intent i = getActivity().getIntent();
//       UserInfoModel userInfoModel = (UserInfoModel) i.getSerializableExtra("userInfo");
//        binding.tvdailyCalories.setText((String.valueOf(userInfoModel.getMaintainCalories())));
        return root;
    }

    private void signout () {

        googleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete (@NonNull Task<Void> task) {

                startActivity(new Intent((Activity)getActivity(), LoginActivity.class));
            }
        });
    }

    @Override
    public void onDestroyView () {
        super.onDestroyView();
        binding = null;
    }

    private void setRecyclerView(View root){
        RecyclerView recyclerView = root.findViewById(R.id.recycler_view_todays_diet);
        ArrayList<FoodItemModel> todaysDiet = new ArrayList<>();
        setFoodItems(todaysDiet);
        FoodListRecyclerViewAdapter foodListRecyclerViewAdapter = new FoodListRecyclerViewAdapter(getActivity(),todaysDiet);
        recyclerView.setAdapter(foodListRecyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

    }
    private void setFoodItems(ArrayList<FoodItemModel> todaysDiet){

          todaysDiet.add(new FoodItemModel("Milk","155","1 L"));
          todaysDiet.add(new FoodItemModel("Chicken","478","200g"));
          todaysDiet.add(new FoodItemModel("Paneer","296","100g"));
          todaysDiet.add(new FoodItemModel("Rice","260","200g"));
          todaysDiet.add(new FoodItemModel("Brown Bread","313","100g"));
          todaysDiet.add(new FoodItemModel("Cheese","402","100g"));
          todaysDiet.add(new FoodItemModel("Banana","180","2"));

    }


}