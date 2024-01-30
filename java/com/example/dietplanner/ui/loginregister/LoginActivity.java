package com.example.dietplanner.ui.loginregister;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.dietplanner.MainActivity;
import com.example.dietplanner.UserInfoActivity;
import com.example.dietplanner.databinding.ActivityLoginBinding;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class LoginActivity extends AppCompatActivity {
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://dietplanner-b6460-default-rtdb.firebaseio.com/");
    private ActivityLoginBinding binding;
    GoogleSignInOptions googleSignInOptions;
    GoogleSignInClient googleSignInClient;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        sharedPreferences = getSharedPreferences("user_info",MODE_PRIVATE);

        AppCompatTextView prefixView = binding.etLoginContact.findViewById(com.google.android.material.R.id.textinput_prefix_text);
        prefixView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
        prefixView.setGravity(Gravity.CENTER_VERTICAL);

        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleSignInClient = GoogleSignIn.getClient(this,googleSignInOptions);


        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if(acct!=null){
            finish();
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        }


        if(sharedPreferences.contains("contact")){
            finish();
            startActivity(new Intent(LoginActivity.this,UserInfoActivity.class));
        }
        if(sharedPreferences.contains("contact")){
            finish();
            startActivity(new Intent(LoginActivity.this,MainActivity.class));
        }


        binding.btnGoogleSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                googleSignIn();
            }
        });


        binding.etLoginPassword.getPasswordVisibilityToggleDrawable();

        //TODO Email validation ,warning text in et
        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                String loginContact = getContact(binding.etLoginContact.getEditText());
                String loginPassword = getPassword(binding.etLoginPassword.getEditText());
                
                if(loginPassword.isEmpty()|| loginContact.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Email or Password can not be empty", Toast.LENGTH_SHORT).show();
                }else {
                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange (@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild(loginContact)){
                                String getPassword = snapshot.child(loginContact).child("password").getValue(String.class);

                                if(getPassword.equals(loginPassword)){
                                    String firstname = snapshot.child(loginContact).child("firstname").getValue(String.class);
                                    Boolean detailfilled = snapshot.child(loginContact).child("detailfilled").getValue(Boolean.class);

                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("contact",loginContact);
                                    editor.putString("firstname",firstname);
                                    editor.putBoolean("detailfilled", Boolean.TRUE.equals(detailfilled));
                                    editor.apply();


                                    startActivity(new Intent(LoginActivity.this, UserInfoActivity.class));
                                }else {
                                    Toast.makeText(LoginActivity.this, "Incorrect Password", Toast.LENGTH_SHORT).show();
                                }

                            }else {
                                Toast.makeText(LoginActivity.this, "Enter registered Mobile no.", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled (@NonNull DatabaseError error) {

                        }
                    });

                }
            }   
        });

        binding.tvLoginNewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                finish();
                startActivity(new Intent(LoginActivity.this ,RegisterActivity.class));
            }
        });
        
        
    
    
    }

    private void googleSignIn () {
    Intent intent = googleSignInClient.getSignInIntent();
    startActivityForResult(intent,1000);


    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1000){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                task.getResult(ApiException.class);
                startActivity(new Intent(LoginActivity.this,UserInfoActivity.class));
            } catch (ApiException e) {

                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private String getContact(EditText etContact){
        return etContact.getText().toString().trim();
    }

    private String getPassword(EditText etPassword){
        return etPassword.getText().toString().trim();
    }
}