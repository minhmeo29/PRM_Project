package com.example.foodapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.common.api.ApiException;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodapp.model.UserModel;
import com.example.foodapp.databinding.ActivityLoginBinding;
import com.google.android.gms.auth.api.signin.*;
import com.google.android.gms.common.api.ApiException;
import com.google.firebase.auth.*;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    private String userName = null;
    private String email;
    private String password;
    private FirebaseAuth auth;
    private DatabaseReference database;
    private GoogleSignInClient googleSignInClient;
    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
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

        // Button: login
        binding.loginButton.setOnClickListener(v -> {
            email = binding.editTextTextEmailAddress.getText().toString().trim();
            password = binding.editTextTextPassword.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter all the details 😒", Toast.LENGTH_SHORT).show();
            }
            else {
                createUser();
                Toast.makeText(this, "Login successful 😁", Toast.LENGTH_SHORT).show();
            }
        });

        // Button: move to sign up
        binding.dontHaveButton.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);
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
                                                        Toast.makeText(LoginActivity.this, "Sign-in Successful 😁", Toast.LENGTH_SHORT).show();
                                                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                                        finish();
                                                    } else {
                                                        Toast.makeText(LoginActivity.this, "Sign-in Failed 😒", Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                    }
                                } catch (ApiException e) {
                                    Toast.makeText(LoginActivity.this, "Sign-in Failed 😒", Toast.LENGTH_SHORT).show();
                                    e.printStackTrace();
                                }
                            } else {
                                Toast.makeText(LoginActivity.this, "Sign-in Failed 😒", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

    private void createUser() {
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = auth.getCurrentUser();
                        updateUi(user);
                    } else {
                        auth.createUserWithEmailAndPassword(email, password)
                                .addOnCompleteListener(task1 -> {
                                    if (task1.isSuccessful()) {
                                        saveUserData();
                                        FirebaseUser user = auth.getCurrentUser();
                                        updateUi(user);
                                    } else {
                                        Toast.makeText(LoginActivity.this, "Sign-in Failed 😒", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                });
    }

    private void saveUserData() {
        email = binding.editTextTextEmailAddress.getText().toString().trim();
        password = binding.editTextTextPassword.getText().toString().trim();

        UserModel user = new UserModel(userName, email, password, null, null);
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        database.child("user").child(userId).setValue(user);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void updateUi(FirebaseUser user) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
