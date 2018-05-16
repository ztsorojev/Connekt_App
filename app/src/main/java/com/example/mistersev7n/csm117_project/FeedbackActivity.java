package com.example.mistersev7n.csm117_project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class FeedbackActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
    }

    //Go to Timer screen
    public void timerScreen() {
        Intent intent = new Intent(this, TimerActivity.class);
        startActivity(intent);
    }

    /** Called when the user taps the "TOO SLOW" button */
    public void slowButton(View view) {
        // Update database
        timerScreen();
    }

    /** Called when the user taps the "GOOD" button */
    public void goodButton(View view) {
        // Update database
        timerScreen();
    }

    /** Called when the user taps the "TOO FAST" button */
    public void fastButton(View view) {
        // Update database
        timerScreen();
    }


}
