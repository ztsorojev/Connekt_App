package com.example.mistersev7n.csm117_project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

public class CreateSessionActivity extends AppCompatActivity {

    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

    int id;
    TextView myTextView;
    Button sessionButton;
    Button feedbackButton;

    public static String sessionCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_session);

        myTextView = (TextView) findViewById(R.id.sessionID);
        sessionButton = (Button) findViewById(R.id.sessionbutton);
        feedbackButton = (Button) findViewById(R.id.button2);

        // Creates random six-digit session ID number
        Random rand = new Random();
        id = 10000 + rand.nextInt(90000);

        //****************************************
        //CREATES NEW SESSION STRUCTURE IN DATABASE
        //****************************************

        sessionCode = Integer.toString(id);

        sessionButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                myTextView.setText(sessionCode);

                mDatabase.child(sessionCode);
                mDatabase.child(sessionCode).child("Quiz");
                mDatabase.child(sessionCode).child("Feedback");
                mDatabase.child(sessionCode).child("Feedback").child("good").setValue( (long) 0);
                mDatabase.child(sessionCode).child("Feedback").child("fast").setValue( (long) 0);
                mDatabase.child(sessionCode).child("Feedback").child("slow").setValue( (long) 0);

            }
        });

        feedbackButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent1 = new Intent(CreateSessionActivity.this, FeedbackResultsActivity.class);
                startActivity(intent1);
            }
        });


    }
}
