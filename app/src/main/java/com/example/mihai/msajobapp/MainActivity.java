package com.example.mihai.msajobapp;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private FirebaseAuth mAuth;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference;
    FirebaseUser currentUser;
    ListView listOfJobs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listOfJobs =  findViewById(R.id.listViewJobs);

        findViewById(R.id.buttonEditProfile).setOnClickListener(this);
        findViewById(R.id.buttonAddJob).setOnClickListener(this);
        findViewById(R.id.buttonSignOut).setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        databaseReference = database.getReference();
        if(currentUser == null){
            updateUI(currentUser);
        }
        System.out.println(databaseReference);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                showData(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("read failed");
            }
        });

    }

    private void showData(DataSnapshot dataSnapshot) {
        ArrayList<JobInfo> jInfo = new ArrayList<>();
        for(DataSnapshot ds : dataSnapshot.getChildren()){
            if(ds.getKey().toString().equals("jobs")){
                for(DataSnapshot timeStampDS : ds.getChildren()){
                    for(DataSnapshot jobDs : timeStampDS.getChildren()){
                        System.out.println(jobDs);
                        JobInfo newJob = new JobInfo();
                        newJob.setJobTitle(jobDs.getValue(JobInfo.class).getJobTitle());
                        newJob.setShortJobDesc(jobDs.getValue(JobInfo.class).getShortJobDesc());
                        jInfo.add(newJob);
                    }
                }
            }
        }
        HashMap<String, String> jobTitleAndDesc = new HashMap<>();
        /*ArrayList<String> array = new ArrayList<>();
        for(int i=0; i<jInfo.size();i++){
            array.add(jInfo.get(i).getJobTitle());
        }
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,array);
        listOfJobs.setAdapter(adapter);*/

        for(int i=0; i<jInfo.size();i++){
            jobTitleAndDesc.put(jInfo.get(i).getJobTitle(), jInfo.get(i).getShortJobDesc());
        }
        List<HashMap<String, String>> listItems = new ArrayList<>();
        SimpleAdapter adapter = new SimpleAdapter(this, listItems, R.layout.job_list_item,
                new String[]{"First Line", "Second Line"},
                new int[] {R.id.jobTitleText, R.id.jobShortDescText});

        Iterator it = jobTitleAndDesc.entrySet().iterator();
        while (it.hasNext()){
            HashMap<String, String> resultsMap = new HashMap<>();
            Map.Entry pair = (Map.Entry)it.next();
            resultsMap.put("First Line", pair.getKey().toString());
            resultsMap.put("Second Line", pair.getValue().toString());
            listItems.add(resultsMap);
        }

        listOfJobs.setAdapter(adapter);
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
            Intent intent = new Intent(this, AddJob.class);
            startActivity(intent);
        }
        }
    }
