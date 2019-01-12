package com.example.mihai.msajobapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

public class AddJob extends AppCompatActivity implements View.OnClickListener{
    private FirebaseAuth mAuth;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference;
    private String userID;
    FirebaseUser currentUser;
    private EditText mJobTitleField;
    private EditText mJobShortDescField;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_job);

        mJobShortDescField = findViewById(R.id.editJobShortDescription);
        mJobTitleField = findViewById(R.id.editJobTitle);

        findViewById(R.id.buttonPostJob).setOnClickListener(this);
        findViewById(R.id.buttonBackToMain).setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        databaseReference = database.getReference();

        userID = currentUser.getUid();

    }

    public void onStart() {
        super.onStart();
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if(i == R.id.buttonPostJob){
            if(mJobTitleField.getText().toString().isEmpty() || mJobShortDescField.getText().toString().isEmpty()){
                System.out.println("Text fields empty");
            }
            else{
                long id = System.currentTimeMillis();
                String idString = String.valueOf(id);
                databaseReference.child("jobs").child(userID).child(idString).child("jobTitle").setValue(mJobTitleField.getText().toString());
                databaseReference.child("jobs").child(userID).child(idString).child("shortJobDesc").setValue(mJobShortDescField.getText().toString());
                finish();
            }

        }
        if(i == R.id.buttonBackToMain){
            finish();
        }
    }
}
