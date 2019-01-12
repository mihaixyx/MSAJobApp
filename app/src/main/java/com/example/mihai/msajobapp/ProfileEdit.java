package com.example.mihai.msajobapp;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileEdit extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference;
    private EditText mFullNameField;
    private EditText mShortBioField;
    private String userID;
    FirebaseUser currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);

        mFullNameField = findViewById(R.id.fullName);
        mShortBioField = findViewById(R.id.shortBio);


        findViewById(R.id.buttonEditProfileNow).setOnClickListener(this);
        findViewById(R.id.buttonBackToMain).setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        databaseReference = database.getReference();

        System.out.println(databaseReference);
        userID = currentUser.getUid();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                System.out.println(dataSnapshot);
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    UserInfo uInfo = new UserInfo();
                    if(ds.child(userID).getValue(UserInfo.class) == null){
                        break;
                    }
                    uInfo.setFullName(ds.child(userID).getValue(UserInfo.class).getFullName());
                    uInfo.setShortBio(ds.child(userID).getValue(UserInfo.class).getShortBio());
                    mFullNameField.setText(uInfo.getFullName());
                    mShortBioField.setText(uInfo.getShortBio());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("read failed");
            }
        });
    }

    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.



    }
    @Override
    public void onClick(View v) {
        int i = v.getId();
        if(i == R.id.buttonEditProfileNow){
            if(mFullNameField.getText().toString().isEmpty() || mShortBioField.getText().toString().isEmpty()){
                System.out.println("Text fields empty");
            }
            else{
                databaseReference.child("users").child(currentUser.getUid()).child("email").setValue(currentUser.getEmail());
                databaseReference.child("users").child(currentUser.getUid()).child("fullName").setValue(mFullNameField.getText().toString());
                databaseReference.child("users").child(currentUser.getUid()).child("shortBio").setValue(mShortBioField.getText().toString());
                finish();
            }

        }
        if(i == R.id.buttonBackToMain){
            finish();
        }
    }
}
