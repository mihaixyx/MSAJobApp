package com.example.mihai.msajobapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private FirebaseAuth mAuth;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        findViewById(R.id.buttonEditProfile).setOnClickListener(this);
        findViewById(R.id.buttonAddJob).setOnClickListener(this);
        findViewById(R.id.buttonSignOut).setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.


        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);

    }

    private void updateUI(FirebaseUser currentUser) {
        if (currentUser == null) {
            System.out.println("NOT LOGGED IN");
            Intent intent = new Intent(this, Login_Register.class);
            startActivity(intent);
        } else {
            System.out.println(currentUser);

        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.buttonSignOut){
            System.out.println("Sign out button pressed");
            mAuth.signOut();
            updateUI(null);
            }
        if(i == R.id.buttonEditProfile){

            Intent intent = new Intent(this, ProfileEdit.class);
            startActivity(intent);
        }
        if(i == R.id.buttonAddJob){
            System.out.println("List jobs NOW");
        }
        }
    }
