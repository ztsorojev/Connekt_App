package com.example.mistersev7n.csm117_project;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

public class TimerActivity extends AppCompatActivity {

    private static final long START_TIME_IN_MILLIS = 120000;   //20 minutes = 1200000 milli seconds

    private TextView mTextViewCountDown;
    private CountDownTimer mCountDownTimer;
    private boolean mTimerRunning;
    private MaterialProgressBar progressCountdown;

    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);



        mTextViewCountDown = findViewById(R.id.text_view_countdown);
        progressCountdown = findViewById(R.id.progress_countdown);

        //Prevent user from going back
        onBackPressed();

        progressCountdown.setMax((int) START_TIME_IN_MILLIS);
        updateCountDownText(); //because we update from the initialize "00:00" time to the time we defined in mTimeLeftInMillis
        startTimer();
    }

    @Override
    public void onBackPressed() {

    }

    private void startTimer() {
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
                int progress = (int) (START_TIME_IN_MILLIS - mTimeLeftInMillis);
                progressCountdown.setProgress(progress);
            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
                progressCountdown.setProgress(0);
                mTextViewCountDown.setText("Done!");

                //Go back to feedback screen when timer finished
                Intent intent = new Intent(TimerActivity.this, FeedbackActivity.class);
                startActivity(intent);
            }
        }.start();

        mTimerRunning = true;

    }
    private void resetTimer() {
        mTimeLeftInMillis = START_TIME_IN_MILLIS;
        updateCountDownText();
    }

    private void updateCountDownText() {
        //we have to cast into an int because we are using 'long' type of time calculation
        //to cast, just put: (int) at beginning
        int minutes = (int) (mTimeLeftInMillis/1000) / 60;
        int seconds = (int) (mTimeLeftInMillis/1000) - (minutes*60);

        String timeLeftFormat = String.format(Locale.getDefault(),"%02d:%02d", minutes, seconds);

        mTextViewCountDown.setText(timeLeftFormat);

    }


    //************************************************************
    // MENU
    //************************************************************

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.opt_menu, menu);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.quizz:
                Intent intent1 = new Intent(this, Student_Quizlist.class);
                this.startActivity(intent1);
                return true;
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                Intent intent2 = new Intent(this, MainActivity.class);
                this.startActivity(intent2);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
