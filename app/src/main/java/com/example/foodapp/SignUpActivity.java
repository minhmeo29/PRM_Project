package com.example.foodapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodapp.model.UserModel;
import com.example.foodapp.databinding.ActivitySignUpBinding;
import com.google.android.gms.auth.api.signin.*;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.*;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    private String email, password, username;
    private FirebaseAuth auth;
    private DatabaseReference database;
    private GoogleSignInClient googleSignInClient;
    private ActivitySignUpBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Google Sign-In options
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        // Initialize Firebase
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference();
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);

        // Button: Create account
        binding.createAccountButton.setOnClickListener(v -> {
            username = binding.userName.getText().toString();
            email = binding.emailAddress.getText().toString().trim();
            password = binding.password.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty() || username.isEmpty()) {
                Toast.makeText(this, "Please fill all the details", Toast.LENGTH_SHORT).show();
            } else {
                createAccount(email, password);
            }
        });

        // Button: Already have account
        binding.alreadyhavebutton.setOnClickListener(v -> {
            startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
        });

        // Button: Google sign-in
        binding.googleButton.setOnClickListener(v -> {
            Intent signInIntent = googleSignInClient.getSignInIntent();
            launcher.launch(signInIntent);
        });
    }

    // Google Sign-In launcher
    private final ActivityResultLauncher<Intent> launcher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                    new ActivityResultCallback<ActivityResult>() {
                        @Override
                        public void onActivityResult(ActivityResult result) {
                            if (result.getResultCode() == Activity.RESULT_OK) {
                                Intent data = result.getData();
                                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                                try {
                                    GoogleSignInAccount account = task.getResult(ApiException.class);
                                    if (account != null) {
                                        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
                                        auth.signInWithCredential(credential)
                                                .addOnCompleteListener(task1 -> {
                                                    if (task1.isSuccessful()) {
                                                        Toast.makeText(SignUpActivity.this, "Sign-in Successful 😁", Toast.LENGTH_SHORT).show();
                                                        startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                                                        finish();
                                                    } else {
                                                        Toast.makeText(SignUpActivity.this, "Sign-in Failed 😒", Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                    }
                                } catch (ApiException e) {
                                    Toast.makeText(SignUpActivity.this, "Sign-in Failed 😒", Toast.LENGTH_SHORT).show();
                                    Log.e("SignActivity", "Google sign-in error", e);
                                }
                            } else {
                                Toast.makeText(SignUpActivity.this, "Sign-in Failed 😒", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

    private void createAccount(String email, String password) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "Account Created Successfully", Toast.LENGTH_SHORT).show();
                        saveUserData();
                        startActivity(new Intent(this, LoginActivity.class));
                        finish();
//                        Intent intent = new Intent(this, LoginActivity.class);
//                        intent.putExtra("register_success", true); // Truyền thông báo đã đăng ký
//                        startActivity(intent);
//                        finish();
                    } else {
                        Toast.makeText(this, "Account Creation Failed", Toast.LENGTH_SHORT).show();
                        Log.d("Account", "createAccount: Failure", task.getException());
                    }
                });
    }

    private void saveUserData() {
        username = binding.userName.getText().toString();
        email = binding.emailAddress.getText().toString().trim();
        password = binding.password.getText().toString().trim();

        UserModel user = new UserModel(username, email, password, null, null);
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        database.child("users").child(userId).setValue(user);
    }
}