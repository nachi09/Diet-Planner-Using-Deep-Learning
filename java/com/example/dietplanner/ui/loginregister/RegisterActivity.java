package com.example.dietplanner.ui.loginregister;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.dietplanner.MainActivity;
import com.example.dietplanner.R;
import com.example.dietplanner.UserInfoActivity;
import com.example.dietplanner.databinding.ActivityRegisterBinding;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterActivity extends AppCompatActivity {
    private ActivityRegisterBinding binding;
    private SharedPreferences sharedPreferences;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://dietplanner-b6460-default-rtdb.firebaseio.com/");
    //TODO Email validation ,warning text in et
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        sharedPreferences = getSharedPreferences("user_info",MODE_PRIVATE);

        AppCompatTextView prefixView = binding.etRegisterContact.findViewById(com.google.android.material.R.id.textinput_prefix_text);
        prefixView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
        prefixView.setGravity(Gravity.CENTER_VERTICAL);




        binding.btnRegister.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick (View view) {
                String registerEmail =getString(binding.etRegisterEmail.getEditText());

                String registerPassword = getString(binding.etRegisterPassword.getEditText());

                String registerContact = getString(binding.etRegisterContact.getEditText());

                String registerFirstName = getString(binding.etRegisterFirstName.getEditText());
                String registerLastName = getString(binding.etRegisterLastName.getEditText());

                Log.d("REGISTER_",registerEmail.toString());





                if(registerEmail.isEmpty() || registerPassword.isEmpty()|| registerContact.isEmpty() || registerFirstName.isEmpty()||registerLastName.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "Enter All Details", Toast.LENGTH_SHORT).show();
                }else if(registerContact.length()!=10 ){
                    Toast.makeText(RegisterActivity.this, "Contact must be 10 digit", Toast.LENGTH_SHORT).show();
                } else if (registerPassword.length()<8) {
                    binding.etRegisterPassword.setError("Password must have at least 8 characters");

                    
                } else{
                    binding.etRegisterPassword.setErrorEnabled(false);
                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange (@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild(registerContact)){
                                Toast.makeText(RegisterActivity.this, "Contact already registered", Toast.LENGTH_SHORT).show();
                            }else{

                                databaseReference.child("users").child(registerContact).child("email").setValue(registerEmail);
                                databaseReference.child("users").child(registerContact).child("password").setValue(registerPassword);
                                databaseReference.child("users").child(registerContact).child("firstname").setValue(registerFirstName);
                                databaseReference.child("users").child(registerContact).child("lastname").setValue(registerLastName);
                                databaseReference.child("users").child(registerContact).child("newuser").setValue(true);
                                databaseReference.child("users").child(registerContact).child("detailfilled").setValue(false);


                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("contact",registerContact);
                                editor.putString("firstname",registerFirstName);
                                editor.putBoolean("detailfilled",false);
                                editor.apply();

                                finish();
                                startActivity(new Intent(new Intent(RegisterActivity.this, UserInfoActivity.class)));
                            }
                        }

                        @Override
                        public void onCancelled (@NonNull DatabaseError error) {
                            Toast.makeText(RegisterActivity.this, "Check the internet connection", Toast.LENGTH_SHORT).show();
                        }
                    });



                }
            }
        });

        binding.tvRegisterLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                finish();
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
            }
        });



    }




    private String getString(EditText editText){
        return editText.getText().toString();
    }

    private Integer getInteger(EditText editText){
        return Integer.parseInt(editText.getText().toString().trim());
    }


    @Override
    public void onBackPressed () {
        super.onBackPressed();
        startActivity(new Intent(RegisterActivity.this,LoginActivity.class));

    }


//    private void updateInfo(String registerContact, String key, Object value){
//        databaseReference.child("users").child(registerContact).child(key).setValue(value);
//    }


}